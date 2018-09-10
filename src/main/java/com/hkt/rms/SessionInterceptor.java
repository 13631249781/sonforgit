package com.hkt.rms;

import com.hkt.rms.bean.UserStaffParticulars;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserStaffParticulars userBean = (UserStaffParticulars) request.getSession().getAttribute("userBean");
        if (userBean == null) {
            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
                response.setHeader("sessionStatus", "timeout");
            } else {
                response.sendRedirect(request.getContextPath()+"/login");
            }
            return false;
        }
        return true;
    }

}
