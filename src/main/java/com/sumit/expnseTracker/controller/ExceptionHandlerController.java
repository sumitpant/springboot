package com.sumit.expnseTracker.controller;

import com.sumit.expnseTracker.CustomException.ErrorObj;
import com.sumit.expnseTracker.CustomException.ExpenseNotFound;
import com.sumit.expnseTracker.modal.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = ExpenseNotFound.class)
    public ResponseEntity<ErrorObj> exception(ExpenseNotFound exception){
        var errorObj= new ErrorObj(exception.getMessage(),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorObj, HttpStatus.NOT_FOUND);

    }

}
