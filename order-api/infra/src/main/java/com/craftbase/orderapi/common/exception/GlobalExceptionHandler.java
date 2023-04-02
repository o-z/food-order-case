package com.craftbase.orderapi.common.exception;

import io.craftgate.exception.CraftgateException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = OrderApiException.class)
    public ResponseEntity<OrderApiException> orderApiExceptionHandler(OrderApiException e) {
        logger.info("Order Api related exception => {}", e.getMessage());

        return new ResponseEntity<>(e, HttpStatus.valueOf(e.getHttpStatusCode()));
    }

    @ExceptionHandler(value = CraftgateException.class)
    public ResponseEntity<OrderApiException> orderApiExceptionHandler(CraftgateException e) {
        logger.info("CraftGate related exception => {}", e.getMessage());
        return new ResponseEntity<>(new OrderApiException(ErrorCode.PAYMENT_CHANNEL_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
