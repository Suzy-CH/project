package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
//    切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill))")
    public void autoFillPointCut()
    {

    }
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段的填充");
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        AutoFill autoFill=signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType=autoFill.value();
        Object[] args=joinPoint.getArgs();
        if(args==null || args.length==0)
        {
            return;
        }
        Object entity = args[0];
        LocalDateTime now=LocalDateTime.now();
        Long currentId= BaseContext.getCurrentId();
        if(operationType==OperationType.INSERT)
        {
            Method setCreatTime=entity.getClass().getDeclaredMethod("setCreateTime",LocalDateTime.class);
            Method setCreatUser=entity.getClass().getDeclaredMethod("setCreateUser",Long.class);
            Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
            Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
            setCreatTime.invoke(entity,now);
            setCreatUser.invoke(entity,currentId);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }else if(operationType==OperationType.UPDATE)
        {
            Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
            Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }
    }
}