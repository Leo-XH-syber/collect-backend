package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.TaskStatusCode;
import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.WorkerPropertyMapper;
import com.example.backendcollect.mq.MQSender;
import com.example.backendcollect.po.Order;
import com.example.backendcollect.po.Task;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.serviceimpl.recommend.RecStrategyFactory;
import com.example.backendcollect.serviceimpl.recommend.RecommendStrategy;
import com.example.backendcollect.utils.Constant;
import com.example.backendcollect.utils.PageInfoUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.task.TaskVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    TaskMapper taskMapper;

    @Resource
    OrderMapper orderMapper;
    @Resource
    WorkerPropertyMapper workerPreferMapper;
    @Resource
    RecStrategyFactory recStrategyFactory;

    private RecommendStrategy recommendStrategy;

    private ResultHelper resultHelper;

    @Autowired
    public void setMqSender(MQSender mqSender) {
        this.mqSender = mqSender;
    }


    private MQSender mqSender;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    @Override
    public TaskVO createTask(TaskVO taskVO) {
        Task task = new Task(taskVO);
        long timeStamp = (task.getEndTime().getTime() - new Date().getTime());
        // 0 到大约 49 天
        if (timeStamp <= 0) {
            throw new ServiceException(TaskStatusCode.TASK_TIME_INVALID);
        }
        if ((taskMapper.insert(task)) > 0) {
            mqSender.sendLazy(task, timeStamp);
            return new TaskVO(task);
        }
        // 小于零说明出错. 直接抛错, 让全局异常捕获

        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public PageInfo<TaskVO> getTasksByEmployer(Integer employerId, Integer page, Integer taskState) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectByEmployerId(employerId, taskState));
        return getTaskVOPageInfo(employerId, po);
    }

    @Override
    public PageInfo<TaskVO> getTasksByWorker(Integer uid, Integer page, int orderState) {
        // 先从order表获取worker的所有task的id
        List<Task> taskList = new ArrayList<>();
        List<Order> orderList = orderMapper.selectByWorkerId(uid, orderState);

        for (Order order : orderList) {
            taskList.add(taskMapper.selectByPrimaryKey(order.getTaskId()));
        }

        // 再得到分页结果
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskList);
        return getTaskVOPageInfo(uid, po);
    }


    @Override
    public PageInfo<TaskVO> getAllValidTask(Integer page, Integer uid) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectAllValid());
        return getTaskVOPageInfo(uid, po);
    }
    @Override
    public PageInfo<TaskVO> getAllValidTaskDesc(Integer page, Integer uid) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectAllValidDesc());
        return getTaskVOPageInfo(uid, po);
    }

    public PageInfo<TaskVO> getTaskHall(Integer page) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectAllValid());
        return getTaskVOPageInfo(po);
    }

    @Override
    public PageInfo<TaskVO> getAllTask(Integer page) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectAll());
        return getTaskVOPageInfo(po);
    }

    @Override
    public TaskVO getATask(Integer taskId) {
        return new TaskVO(taskMapper.selectByPrimaryKey(taskId));
    }

    @Override
    public List<TaskVO> getRecommendedTask(Integer uid) {
        if (this.recommendStrategy == null)
            this.recommendStrategy = recStrategyFactory.getRecStrategy(RecStrategyType.ALS);
        List<TaskVO> recommendedList = recommendStrategy.recommend(uid);
//        if(recommendedList!=null&& recommendedList.size()>0) return recommendedList;
//        WorkerPrefer workerPrefer=workerPreferMapper.selectByPrimaryKey(uid);
//        if(workerPrefer==null) return recommendedList;
//        WorkerPreferVO workerPreferVO=new WorkerPreferVO(workerPrefer);
//        List<Task> tasks=new ArrayList<>();
//        recommendedList=new ArrayList<>();
//        for(TaskType taskType:workerPreferVO.getPreference()){
//            Integer kind=taskType.ordinal();
//            tasks.addAll(taskMapper.selectByTaskKind(kind,3));
//        }
//        recommendedList=this.convert(tasks,TaskVO.class);
        return recommendedList;

    }

    @Override
    public RecStrategyType setRecommendStrategy(RecStrategyType strategy) {
        this.recommendStrategy = recStrategyFactory.getRecStrategy(strategy);
        return this.recommendStrategy.strategyType;
    }

    @Override
    public RecStrategyType getRecommendStrategy() {
        return this.recommendStrategy.strategyType;
    }


    /*
        -------------------- Private methods --------------------
         */
    private PageInfo<TaskVO> getTaskVOPageInfo(Integer uid, PageInfo<Task> po) {
        PageInfo<TaskVO> result = PageInfoUtil.convert(po, TaskVO.class);

        if (uid != null && uid > 0) {
            List<TaskVO> voList = result.getList();
            // vo.setLiked(this.getCourseLike(uid, vo.getId()).getCode() == 1);
            // vo.setBought(orderService.isBought(uid,vo.getId()));
            // vo.setManageable(uid.equals(vo.getTeacherId()));
        }
        return result;
    }

    private PageInfo<TaskVO> getTaskVOPageInfo(PageInfo<Task> po) {
        PageInfo<TaskVO> result = PageInfoUtil.convert(po, TaskVO.class);
        return result;
    }

    private <P, V> List<V> convert(List<P> pos, Class<V> vClass) {
        List<V> vos = new ArrayList<>();
        try {
            if (pos.size() > 0) {
                Constructor<V> constructor = vClass.getConstructor(pos.get(0).getClass());
                for (P p : pos) {
                    V v = null;
                    try {
                        v = constructor.newInstance(p);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    vos.add(v);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return vos;
    }
}
