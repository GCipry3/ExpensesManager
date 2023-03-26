package com.sd.laborator.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object Database {
    private const val url = "jdbc:mariadb://localhost/lab4"
    private const val user = "root"
    private const val password = "123456789"

    private fun connectToDatabase(): Connection {
        return DriverManager.getConnection(url, user, password)
    }

    private fun executeQueryWithoutResult(sql: String) {
        val conn = connectToDatabase()
        val statement = conn.createStatement()
        statement.execute(sql)
        statement.close()
        conn.close()
    }

    private fun executeQueryWithResult(sql: String): ResultSet {
        val conn = connectToDatabase()
        val statement = conn.createStatement()
        val resultSet = statement.executeQuery(sql)
        conn.close()
        return resultSet
    }

    fun getFamilyMembers() : String{
        val sql = "SELECT id,name FROM person"

        val result = executeQueryWithResult(sql)
        var json = "["
        while(result.next()){
            json += "{\"id\": " + result.getInt("id") + ", \"name\": \"" + result.getString("name") + "\"},"
        }
        json = json.substring(0, json.length - 1)
        json += "]"

        return json
    }

    fun addFamilyMember(name: String, hashedPassword: String){
        val sql = "INSERT INTO person (name, hashedPassword) VALUES ('$name', '$hashedPassword')"

        executeQueryWithoutResult(sql)
    }

    fun getHashedPassword(id: Int): String{
        val sql = "SELECT hashedPassword FROM person WHERE id = $id"

        val result = executeQueryWithResult(sql)
        result.next()
        return result.getString("hashedPassword")
    }
}
