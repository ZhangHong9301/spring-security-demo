package com.zxf.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 11:32
 */
@Aspect
@Component
public class TimeAspect {

    /**
     * UserController.* 表示UserController下所有方法；*(..) 表示方法下所有参数
     */
    @Around("execution(* com.zxf.security.web.controller.UserController.*(..))") /*切入点*/
    /*增强*/
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs();

        for (Object arg : args) {
            System.out.println("arg is " + arg);
        }

        Long start = new Date().getTime();
        Object object = pjp.proceed();
        System.out.println("time aspect 耗时:" + (new Date().getTime() - start));
        System.out.println("time aspect end");
        return object;
    }
}
