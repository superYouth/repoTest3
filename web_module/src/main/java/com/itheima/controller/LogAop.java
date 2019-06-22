package com.itheima.controller;

import com.itheima.domain.SysLog;
import com.itheima.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;

    private Date visitTime;
    private Class visitClass;
    private Method method;
    @Pointcut("execution(* com.itheima.controller.*.*(..))")
    public void pc(){}

    @Before("execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime = new Date();
        visitClass = joinPoint.getTarget().getClass();  //获得访问的具体的类
        String methodName = joinPoint.getSignature().getName();//访问方法的名称

        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            method = visitClass.getMethod(methodName);
        }else{
            Class[] classArgs = new Class[args.length];
            int i = 0;
            for (Object arg : args) {
                classArgs[i++] = arg.getClass();
            }
            method = visitClass.getMethod(methodName,classArgs);
        }
    }
    @After("pc()")
    public void doAfter(JoinPoint joinPoint){
        long time = new Date().getTime()-visitTime.getTime();

        String url = "";
        //获取url
        if(visitClass != null&&method != null&&visitClass != LogAop.class){
            //1.获取类上的@RequestMapping
            RequestMapping classAnnotation = (RequestMapping) visitClass.getAnnotation(RequestMapping.class);
            //2.获取方法上的@RequestMapping
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            url = classAnnotation.value()[0]+methodAnnotation.value()[0];

            //获取访问的ip
            String ip = request.getRemoteAddr();
            //获取当前操作的用户
            SecurityContext context = SecurityContextHolder.getContext();//获取SecurityContext
            User user = (User) context.getAuthentication().getPrincipal();
            String username = user.getUsername();

            //将日志相关信息封装到SysLog对象
            SysLog sysLog = new SysLog();
            sysLog.setUsername(username);
            sysLog.setIp(ip);
            sysLog.setExecutionTime(time);
            sysLog.setMethod(visitClass.getName()+"."+method.getName());
            sysLog.setUrl(url);
            sysLog.setVisitTime(visitTime);

            //调用service层的保存方法
            sysLogService.saveLog(sysLog);
        }
    }
}
