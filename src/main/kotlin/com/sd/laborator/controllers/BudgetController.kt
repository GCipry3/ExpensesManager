package com.sd.laborator.controllers

import com.sd.laborator.interfaces.BudgetHandlerInterface
import com.sd.laborator.pojo.MatchPasswordModel
import com.sd.laborator.utils.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.sd.laborator.pojo.Expense


@RestController
class BudgetController {
    @Autowired
    private lateinit var budgetHandler: BudgetHandlerInterface

    @RequestMapping(value = ["/family"], method = [RequestMethod.GET], produces=["application/json"])
    fun getFamilyMembers() :String{
        return Database.getFamilyMembers()
    }

    @RequestMapping(value = ["/family/newMember"], method = [RequestMethod.POST])
    fun addFamilyMember(@RequestParam name: String, @RequestParam password:String){
        val restTemplate = RestTemplate()
        val encryptedResponse = restTemplate.getForObject("http://localhost:2030/encrypt?password=$password", String::class.java)
        val hashedPassword = encryptedResponse?.replace("\"", "") ?: throw Exception("Could not encrypt password")
        Database.addFamilyMember(name, hashedPassword)
    }

    @RequestMapping(value = ["/family/login"], method = [RequestMethod.POST], produces = ["application/json"])
    fun loginFamilyMember(@RequestParam id: Int, @RequestParam password: String): String {
        val restTemplate = RestTemplate()
        val hashedPassword = Database.getHashedPassword(id)
        val matchPassword = restTemplate.postForObject(
            "http://localhost:2030/match",
            MatchPasswordModel(password, hashedPassword),
            String::class.java
        )

        val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
        return if (matchPassword == "true") {
            objectMapper.writeValueAsString(
                hashMapOf(
                    "login" to "true",
                )
            )
        } else {
            objectMapper.writeValueAsString(
                hashMapOf(
                    "login" to "false",
                )
            )
        }
    }


    @RequestMapping(value = ["/family/budget"], method = [RequestMethod.GET], produces=["application/json"])
    fun getBudget() : String{
        val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
        return objectMapper.writeValueAsString(
            hashMapOf(
                "budget" to budgetHandler.getBudget()
            )
        )
    }

    @RequestMapping(value = ["/family/addMoney"], method = [RequestMethod.POST])
    fun addBudget(@RequestParam amount: Int){
        budgetHandler.addMoney(amount)
    }

    @RequestMapping(value = ["/family/spend"], method = [RequestMethod.POST])
    fun spendMoney(@RequestParam id: Int){
        budgetHandler.spend(expenseId = id)
    }

    @RequestMapping(value = ["/family/expenses"], method = [RequestMethod.GET], produces=["application/json"])
    fun getExpenses() : String{
        return budgetHandler.getExpenses().toString()
    }

    @RequestMapping(value = ["/family/expenses/newExpense"], method = [RequestMethod.POST])
    fun addExpense(@RequestParam name: String, @RequestParam price: Double){
        budgetHandler.addExpense(Expense(id=0 , name = name, price = price))
    }
}