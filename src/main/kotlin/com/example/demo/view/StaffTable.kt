package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.model.User
import com.example.demo.model.UserModel
import javafx.collections.ObservableList
import javafx.scene.layout.HBox
import tornadofx.*

class StaffTable: View ("Staff") {
        val personController: PersonController by inject()
        var userTable : TableViewEditModel<UserModel> by singleAssign()
        var users : ObservableList<UserModel> by singleAssign()

        override val root = borderpane {
            users = personController.users

            center = vbox {
                buttonbar {
                    button ("Delete Selected User") {
                        action {
                            val model = userTable.tableView.selectedItem
                            when (model) {
                                null -> return@action
                                else -> personController.deleteUser(model)
                            }
                        }
                    }

                    button ("Commit"){
                        action {
                            personController.commitDirty(userTable.items.asSequence())
                        }
                    }

                    button ("Rollback") {
                        action {
                            userTable.rollback()
                        }
                    }
                }
            }

            center = tableview<UserModel> {
                userTable = editModel
                items = users

                enableCellEditing()
                enableDirtyTracking()

                column("username", UserModel::username)
                column("password", UserModel::password)

                columnResizePolicy = SmartResize.POLICY
            }
        }
    }