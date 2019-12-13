package com.blog.aspect;

import com.blog.consts.WebSocketConsts;
import com.blog.util.FileUtil;
import com.blog.vo.FileContentVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;


@Aspect
@Component
public class BaseRestAspect {

    private final Logger log = LoggerFactory.getLogger(BaseRestAspect.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileUtil fileUtil;


    @Pointcut("execution(* com.blog.controller.*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        String enterStr = MessageFormat.format("Enter: {0}.{1}() with argument[s] = {2}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        fileUtil.writeObj(null,"系统日志", enterStr);
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            String exitStr = MessageFormat.format("Exit: {0}.{1}() with result = {2}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result);
            fileUtil.writeObj(null, WebSocketConsts.SYSTEM_FILE_NAME, exitStr);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}
