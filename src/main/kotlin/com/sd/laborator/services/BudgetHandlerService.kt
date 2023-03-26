package com.sd.laborator.services

import com.sd.laborator.interfaces.BudgetHandlerInterface
import com.sd.laborator.pojo.Expense
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule


@Service
class BudgetHandlerService: BudgetHandlerInterface {
    private var budget = 1000.0

    companion object{
        val expenses = arrayOf(
            Expense(1, "Groceries", 100.0),
            Expense(2, "Bills", 400.0),
            Expense(3, "School", 150.0),
            Expense(4, "Entertainment", 200.0)
        )
    }

    private val expensesList = ConcurrentHashMap<Int, Expense>(expenses.associateBy{
        expense: Expense -> expense.id
    })

    override fun spend(expenseId: Int): Boolean {
        val expense = expensesList[expenseId]!!
        if (expense.price > budget){
            return false
        }
        budget -= expense.price
        return true
    }

    override fun addExpense(expense: Expense): Boolean {
        expense.id= expensesList.size + 1
        expensesList[expense.id] = expense
        return true
    }

    override fun getExpenses(): String {
        val expenses = mutableMapOf<String, Map<String, Any>>()

        expensesList.forEach { (id, expense) ->
            expenses[id.toString()] = mapOf(
                "id" to expense.id,
                "name" to expense.name,
                "price" to expense.price
            )
        }

        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.writeValueAsString(expenses)
    }




    override fun getExpense(expenseId: Int): Expense {
        return expensesList[expenseId]!!
    }

    override fun getBudget(): Double {
        return budget
    }


    override fun addMoney(amount: Int) {
        budget += amount
    }
}