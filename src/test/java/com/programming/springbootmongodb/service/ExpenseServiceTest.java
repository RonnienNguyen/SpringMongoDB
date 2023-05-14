package com.programming.springbootmongodb.service;

import com.programming.springbootmongodb.EntityNotFoundException;
import com.programming.springbootmongodb.model.Expense;
import com.programming.springbootmongodb.model.ExpenseCategory;
import com.programming.springbootmongodb.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    public void setUp() {
        expenseRepository = mock(ExpenseRepository.class);
        expenseService = new ExpenseService(expenseRepository);
    }


    //Version 1
//    @Test
//    public void testAddExpense() {
//        Expense expense = new Expense();
//        expense.setExpenseName("Rent");
//        expense.setExpenseCategory(ExpenseCategory.ENTERTAINMENT);
//        expense.setExpenseAmount(BigDecimal.valueOf(1000.0));
//        expenseService.addExpense(expense);
//        verify(expenseRepository, times(1)).insert(expense);
//    }

    //Version 2
    @Test
    public void testAddExpense() {
        Expense expense = new Expense();
        expense.setExpenseName("Lunch");
        expense.setExpenseCategory(ExpenseCategory.ENTERTAINMENT);
        expense.setExpenseAmount(BigDecimal.valueOf(1000.000));
        when(expenseRepository.insert(expense)).thenReturn(expense);
        expenseService.addExpense(expense);
        // Verify that the expense was added to the database
        verify(expenseRepository).insert(expense);
    }


    //Version 1
    @Test
    public void testUpdateExpense() {
        String id = "1";
        Expense expense = new Expense();
        expense.setId(id);
        expense.setExpenseName("NetFlix");
        expense.setExpenseAmount(BigDecimal.valueOf(10000.0));
        expense.setExpenseCategory(ExpenseCategory.ENTERTAINMENT);
        Expense savedExpense = new Expense();
        savedExpense.setId(id);
        savedExpense.setExpenseName("SavedExpensed");
        savedExpense.setExpenseAmount(BigDecimal.valueOf(10000.0));
        savedExpense.setExpenseCategory(ExpenseCategory.GROCERIES);
        when(expenseRepository.findById(id)).thenReturn(Optional.of(savedExpense));

        expenseService.updateExpense(expense);

        verify(expenseRepository, times(1)).findById(id);
        verify(expenseRepository, times(1)).save(expense);
        assertEquals(expense.getExpenseName(), savedExpense.getExpenseName());
        assertEquals(expense.getExpenseCategory(), savedExpense.getExpenseCategory());
        assertEquals(expense.getExpenseAmount(), savedExpense.getExpenseAmount(), String.valueOf(0.0));
    }

    //Version 2
//    @Test
//    public void testUpdateExpense() {
//        // Create a mock expense repository
//        ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);
//
//        // Create an expense object
//        Expense expense = new Expense();
//        String id = "1";
//        expense.setId(id);
//        expense.setExpenseName("Lunch");
//        expense.setExpenseCategory(ExpenseCategory.ENTERTAINMENT);
//        expense.setExpenseAmount(BigDecimal.valueOf(1000.000));
//
//        // When the expenseRepository.findById(expenseId) method is called, return the expense object
//        Optional<Expense> optionalExpense = Optional.of(expense);
//        when(expenseRepository.findById(expense.getId())).thenReturn(optionalExpense);
//
//        // Create an instance of the ExpenseService class
//        ExpenseService expenseService = new ExpenseService(expenseRepository);
//
//        // Call the updateExpense() method
//        expenseService.updateExpense(expense);
//
//        // Verify that the expenseRepository.findById(expenseId) method was called
//        Mockito.verify(expenseRepository).findById(expense.getId());
//
//        // Verify that the updateExpense() method returned the expense object
//        assertEquals("Lunch", expense.getExpenseName());
//        assertEquals(ExpenseCategory.ENTERTAINMENT, expense.getExpenseCategory());
//        assertEquals(BigDecimal.valueOf(1000.0), expense.getExpenseAmount());
//    }

    @Test
    public void testGetExpenseByName() {
        String name = "Expense 1";
        Expense expense = new Expense();
        expense.setExpenseName(name);
        expense.setExpenseCategory(ExpenseCategory.ENTERTAINMENT);
        expense.setExpenseAmount(BigDecimal.valueOf(100.000));
        when(expenseRepository.findByName(name)).thenReturn(Optional.of(expense));
        Expense result = expenseService.getExpenseByName(name);
        verify(expenseRepository, times(1)).findByName(name);
        assertEquals(expense, result);
    }

    @Test
    public void testGetAllExpense() {

        Expense expense1 = new Expense();
        expense1.setId("1");
        expense1.setExpenseName("Expense 1");
        expense1.setExpenseCategory(ExpenseCategory.RESTAURANT);
        expense1.setExpenseAmount(BigDecimal.valueOf(1000.000));

        Expense expense2 = new Expense();
        expense2.setId("2");
        expense2.setExpenseName("Expense 2");
        expense2.setExpenseCategory(ExpenseCategory.GROCERIES);
        expense2.setExpenseAmount(BigDecimal.valueOf(1000.000));


        List<Expense> expenseList = Arrays.asList(expense1, expense2);

        when(expenseRepository.findAll()).thenReturn(expenseList);

        List<Expense> result = expenseService.getAllExpense();

        verify(expenseRepository, times(1)).findAll();
        assertEquals(expenseList, result);
    }

    @Test
    public void testDeleteExpense() {
        String id = "1";
        expenseService.deleteExpense(id);
        verify(expenseRepository, times(1)).deleteById(id);
    }


}