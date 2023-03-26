package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Expense

interface BudgetHandlerInterface {
    fun spend(expenseId: Int): Boolean
    fun addExpense(expense: Expense): Boolean
    fun getExpenses(): String
    fun getExpense(expenseId: Int): Expense
    fun addMoney(amount: Int)
    fun getBudget(): Double
}