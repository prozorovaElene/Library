package ge.bog.library.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("execution(* ge.bog.library.controllers.*.*(..)) || execution(* ge.bog.library.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(this::convertToString)
                .collect(Collectors.joining(", "));
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), args);
    }

    @AfterReturning(pointcut = "execution(* ge.bog.library.controllers.*.*(..)) || execution(* ge.bog.library.services.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String resultString = convertToString(result);
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), resultString);
    }

    @AfterThrowing(pointcut = "execution(* ge.bog.library.controllers.*.*(..)) || execution(* ge.bog.library.services.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), error.getMessage());
    }

    private String convertToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return String.valueOf(obj);
        }
    }
}
