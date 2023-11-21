package kb.wgwg.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* kb.wgwg.*.controller.*.*(..))")
    public void beforeLog(JoinPoint joinPoint){
        //메서드 실행 전에 출력되는 코드
        logger.info("[Start Executing]: " + joinPoint.getSignature());

        //전달된 파라미터 출력
        Object[] methodArgs = joinPoint.getArgs();
        if(methodArgs != null && methodArgs.length > 0){
            logger.info("[Method Args ...]: " + Arrays.toString(methodArgs));
        }
    }

    @After("execution(* kb.wgwg.*.controller.*.*(..))")
    public void afterLog(JoinPoint joinPoint){
        //메서드 실행 후에 출력되는 로그
        logger.info("[End Executing]: " + joinPoint.getSignature());
    }
}
