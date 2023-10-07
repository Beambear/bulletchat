package com.bulletchat.handler;

import com.bulletchat.code.StatusCode;
import com.bulletchat.domain.JsonResponse;
import com.bulletchat.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

    @ExceptionHandler(ConditionException.class)
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request, ConditionException e){
        return new JsonResponse<>(e.getCode(),e.getMessage(),null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public JsonResponse<String> otherExceptionHandler(HttpServletRequest request, Exception e) {
        return new JsonResponse<>(StatusCode.OTHER_EXCEPTION.getCode(),e.getMessage(),null);
    }
}
