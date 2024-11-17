package com.sumit.expnseTracker.CustomException;


public class ExpenseNotFound  extends RuntimeException{

    public ExpenseNotFound(String message){
        super(message);
    }

}
