package com.example.backendcollect.auth.interceptor;

import com.example.backendcollect.auth.annotation.Authentication;
import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.auth.service.JwtTokenService;
import com.example.backendcollect.enums.errorcode.Impl.UserStatusCode;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Rikka
 * @date: 2022/3/2 上午1:20
 * @description 关于权限鉴定这件事: Spring Security 就可以的, 但是我写这个的时候还不知道...
 * 哈哈能用就行
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    JwtTokenService jwtTokenService;


    private JwtInfo jwtInfo;

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }


    /**
     * 在 controller 之前被调用
     *
     * @param request  请求地址, head, 参数, body
     * @param response
     * @param handler  controller 方法名
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Authentication methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(Authentication.class);
        // 不存在这个注解, 或者注解存在, 但被设置为 false, 直接通过
        if (methodAnnotation == null || !methodAnnotation.required()) {
            return true;
        }

        // 执行认证
        Cookie[] cookies = request.getCookies();
        // cookie 为空, 当然不能通过认证
        if (cookies == null) {
            jwtInfo.setNull();
            throw new ServiceException(UserStatusCode.TOKEN_INVALID);
        }

        Cookie token = new Cookie("token", "");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie;
                break;
            }
        }
        // 没有 token
        if (token.getValue().equals("")) {
            jwtInfo.setNull();
            throw new ServiceException(UserStatusCode.TOKEN_INVALID);
        }

        // 拿到用户信息, 传给需要的 controller
        JwtInfo newJwtInfo = jwtTokenService.getInfoFromToken(token.getValue());

        // token 里面没有需要的东西
        if (newJwtInfo.getId() == null) {
            jwtInfo.setNull();
            throw new ServiceException(UserStatusCode.TOKEN_INVALID);
        }

        // 到了这里说明已经登录了

        // 判断特权等级
        UserRole[] roles = methodAnnotation.allowedRoles();
        UserRole role = newJwtInfo.getUserRole();
        // 相当于 for...else
        found:
        {
            for (UserRole userRole : roles) {
                // 如果注解要求的角色与登录的角色相同或者登录的角色是管理员
                if (userRole.equals(role) || role.equals(UserRole.ADMIN))
                    break found;
            }
            throw new ServiceException(UserStatusCode.INSUFFICIENT_PRIVILEGES);
        }

        BeanCopierWithCacheUtil.copy(newJwtInfo, jwtInfo);

        return true;
    }
    // 好像不生效

    /**
     * controller 执行完毕后, 返回到前端前执行.  每次请求都会更新 cookie
     * 无效! 见下文
     * Note that postHandle is less useful with @ResponseBody and ResponseEntity methods for which the response is written and committed within the HandlerAdapter and before postHandle. That means it is too late to make any changes to the response, such as adding an extra header. For such scenarios, you can implement ResponseBodyAdvice and either declare it as an Controller Advice bean or configure it directly on RequestMappingHandlerAdapter.
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if(jwtInfo.getId()==null)
//            return;
//        Cookie token = new Cookie("token", jwtTokenService.generateToken(jwtInfo) );
//        response.addCookie(token);
//    }
}
