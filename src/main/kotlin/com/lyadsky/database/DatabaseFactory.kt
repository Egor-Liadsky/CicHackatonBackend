package com.lyadsky.database

import com.lyadsky.database.entity.UsersEntity
import com.lyadsky.utils.Constants
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){
        val driverName = "com.mysql.cj.jdbc.Driver"
        val jdbcUrl = "jdbc:mysql://localhost:3306/${Constants.DATABASE_NAME}"
        Database.connect(jdbcUrl, driverName, user = Constants.USER_DATABASE, password = Constants.PASSWORD_DATABASE)

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UsersEntity)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }
}