package com.example.demo.util

import com.example.demo.model.AdminStaffTbl
import com.example.demo.model.DegreeStudentsTbl
import com.example.demo.model.DiplomaStudentsTbl
import com.example.demo.model.ExpensesEntryTbl
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection
import javax.xml.validation.Schema

private var LOG_TO_CONSOLE: Boolean = false

//connection
fun newTransaction(): Transaction = TransactionManager.currentOrNew(Connection.TRANSACTION_SERIALIZABLE).apply {
    if (LOG_TO_CONSOLE) addLogger(StdOutSqlLogger)
}

fun enableConsoleLogger() {
    LOG_TO_CONSOLE = true
}

fun createTables() {
    with(newTransaction()) {

        SchemaUtils.create(ExpensesEntryTbl)
        SchemaUtils.create(DegreeStudentsTbl)
        SchemaUtils.create(DiplomaStudentsTbl)
        SchemaUtils.create(AdminStaffTbl)
    }
}

fun <T> execute(command: () -> T) : T {
    with(newTransaction()) {
        return command().apply {
            commit()
            close()
        }
    }
}
