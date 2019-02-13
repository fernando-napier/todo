package com.microsoft.springframework.samples.utils;

import com.microsoft.springframework.samples.dao.ServiceConfigRepository;
import com.microsoft.springframework.samples.model.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class RestInterceptor implements HandlerInterceptor {

    @Autowired
    private ServiceConfigRepository serviceConfigRepo;

    /**
     * This class is just for validating the calls to the api
     * basically just checking that the request contains the valid api token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map map = request.getParameterMap();
        String tokenString = request.getParameter("key");

        if (StringUtils.isEmpty(tokenString))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");

        List<ServiceConfig> configs = serviceConfigRepo.findByKey("key");

        if (CollectionUtils.isEmpty(configs))
            return true;

        ServiceConfig token = configs.get(0);

        if (token == null || !tokenString.equalsIgnoreCase(token.getValue())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Forbidden");
        }

        return true;
    }

}
