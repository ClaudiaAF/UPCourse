package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.model.UserModel
import com.example.demo.model.Users
import com.example.demo.view.Person
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.sql.Connection

class PersonController : Controller() {
    val users : ObservableList<UserModel> by lazy {
        transaction {
            User.all().map {
                UserModel().apply{
                    item = it
                }
            }.observable()
        }
    }

    init {
        Database.connect("jdbc:sqlite:file:data.sqlite", driver= "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    fun setUp(){
        Database.connect("jdbc:sqlite:file:data.sqlite", driver= "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)

            User.new {
                username = "bob"
                password = "1234"
            }

            User.new {
                username = "foo"
                password = "bar"
            }
        }

        transaction {
            User.all().forEach{ println(it)}
        }
    }

    fun deleteUser(model : UserModel) {
        transaction {
            model.item.delete()
        }
        users.remove(model)
    }

    fun addUser(username: String, password : String){
        transaction {
            val user = User.new {
                this.username = username
                this.password = password
            }
            users.add(
                UserModel().apply {
                    item = user
                }
            )
        }
    }

    fun commitDirty(modelDirtyMappings: Sequence<Map.Entry<UserModel, TableColumnDirtyState<UserModel>>>) {
        transaction {
            modelDirtyMappings.filter { it.value.isDirty }.forEach {
                it.key.commit()
                it.value.commit()
            }
        }
    }
}