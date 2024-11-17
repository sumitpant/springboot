package com.sumit.expnseTracker.controller;

import com.sumit.expnseTracker.CustomException.ExpenseNotFound;
import com.sumit.expnseTracker.commonResponse.Response;
import com.sumit.expnseTracker.entity.ExpenseEntity;
import com.sumit.expnseTracker.modal.Categories;
import com.sumit.expnseTracker.modal.Expense;
import com.sumit.expnseTracker.services.ExpenseService;
import com.sumit.expnseTracker.stub.ExpenseStub;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController {
    private ExpenseService expenseService;


    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;

    }



    @GetMapping("/")
    public ResponseEntity<List<Expense>> getAllExpenses(){
         List<ExpenseEntity> entityList= expenseService.getAllExpenses();
         List<Expense> result = new ArrayList<>();
         if(entityList==null){
             return new ResponseEntity<List<Expense>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
         }
        entityList.forEach(data->{
            var expense= new Expense();
            expense.setId(data.getId());
            expense.setAmount(data.getAmount());
            expense.setDescription(data.getDescription());
            expense.setCreatedOn(data.getCreatedOn());
            expense.setModifiedOn(data.getModifiedOn());
            result.add(expense);
        });
        return new ResponseEntity<List<Expense>>(result, HttpStatus.ACCEPTED);

    }

    @PostMapping("/update")
    public ResponseEntity<Response<String, Null>> updateExpense(@Validated @RequestBody Expense expense){
      var index=  expense.getId()-1;
      List<Expense> expenses=ExpenseStub.expenseList;
      var responseBody= new Response<String,Null>("Invalid Id","Not_Found",HttpStatus.BAD_REQUEST,null);

      if(index>=expenses.size()){
          return  new ResponseEntity<Response<String,Null>>(responseBody,HttpStatus.BAD_REQUEST);
      }
      var expense_i   = expenses.get(index);
        expense_i.setDescription(expense.getDescription());
        expense_i.setAmount(expense.getAmount());
        expense_i.setModifiedOn(new Date());

     responseBody= new  Response<String,Null>("Expense Updated","00",HttpStatus.OK,null);

        return new ResponseEntity<Response<String,Null>>(responseBody,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String,Null>> deleteExpense(@PathVariable(name="id")  int id ){
        List<Expense> expenses=ExpenseStub.expenseList;
        var index= id-1;
        var responseBody= new Response<String,Null>("Invalid Id","Not_Found",HttpStatus.BAD_REQUEST,null);

     if(index>=expenses.size()){
         return new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);
     }

        expenses.remove(index);
        responseBody= new  Response<String,Null>("Expense Updated","00",HttpStatus.OK,null);
        return new ResponseEntity<Response<String,Null>>(responseBody,HttpStatus.OK);


    }

    @GetMapping("/getExpense/{id}")
    public ResponseEntity<Response<String,Expense>> getExpenseById(@PathVariable(name="id") int id)  {

       ExpenseEntity entity= expenseService.getExpenseById(id);
       Expense expense= new Expense();
       expense.setId(entity.getId());
       expense.setAmount(entity.getAmount());
       expense.setDescription(entity.getDescription());
       expense.setCreatedOn(entity.getCreatedOn());
       expense.setCategories(entity.getCategories().name());


       var responseBody= new  Response<String,Expense>("","00",HttpStatus.OK,expense);
        return new ResponseEntity<Response<String,Expense>>(responseBody,HttpStatus.OK);


    }

    @PostMapping("/add-expense")
    public ResponseEntity<Response<String , Null>> addExpense( @Validated @RequestBody Expense expense ){

      var isAdded=  this.expenseService.addExpense(expense);
      var responseBody = new Response<String , Null>("Expense added successfully","00",HttpStatus.OK,null);

      if(isAdded){
          return new ResponseEntity<>(responseBody,HttpStatus.OK);
      }
        responseBody = new Response<>("Expense Not added","01",HttpStatus.INTERNAL_SERVER_ERROR,null);
        return new ResponseEntity<>(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
