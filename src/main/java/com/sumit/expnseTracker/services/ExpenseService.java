package com.sumit.expnseTracker.services;

import com.sumit.expnseTracker.CustomException.ExpenseNotFound;
import com.sumit.expnseTracker.entity.ExpenseEntity;
import com.sumit.expnseTracker.modal.Categories;
import com.sumit.expnseTracker.modal.Expense;
import com.sumit.expnseTracker.repository.ExpenseRepository;
import com.sumit.expnseTracker.stub.ExpenseStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public Expense getById(int id){
        repo.findById(id);
        var list= ExpenseStub.expenseList;
        return  list.get(id);
    }

    public boolean addExpense(Expense expense){
        ExpenseEntity expenseEntity= new ExpenseEntity();
        expenseEntity.setAmount(expense.getAmount());

      Categories c=  Arrays.stream(Categories.values()).filter(data->data.name().equals(expense.getCategories())).findFirst().orElse(null);
        expenseEntity.setCategories(c);
        System.out.println("categories"+c);
        expenseEntity.setCreatedOn(new Date());
        expenseEntity.setModifiedOn(new Date());
        expenseEntity.setDescription(expense.getDescription());
        try{
            ExpenseEntity item=  repo.saveAndFlush(expenseEntity);
            if(item == null) return  false;
            return true;
        }catch(Exception e){
            return false;

        }
    }

    public List<ExpenseEntity> getAllExpenses(){
        try{
            return repo.findAll();
        }catch(Exception e){
            return null;

        }
    }

    public ExpenseEntity getExpenseById(int id) throws ExpenseNotFound {
          return  repo.findById(id).orElseThrow(()->new ExpenseNotFound("Product Not available"));
    }
}
