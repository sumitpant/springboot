package com.sumit.expnseTracker.aop;




import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class ExpenseAspect {

    private static final Logger logger =  LoggerFactory.getLogger(ExpenseAspect.class);

    @Before("execution(* com.sumit.expnseTracker.*..*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        logger.info("Before " + joinPoint.getSignature().getName());

    }


    // * any class under services
    // .. any class under subpackages

    /**
     *
     * run any package inside expenseTracker (eg services)
     * witihn that package run any class or subclass within services subpackages
     * and any method with 0 or more params
     */
   @After("execution(* com.sumit.expnseTracker.*.*..*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        logger.info("After " + joinPoint.getSignature().getName());

    }
    @AfterReturning("execution(* com.sumit.expnseTracker.controller..*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.info("After returning " + joinPoint.getSignature().getName());
    }

}
