package com.example.demo.view

import com.example.demo.controller.AdminController
import com.example.demo.controller.DegreeStudentController
import com.example.demo.model.AdminEntryModel
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.ExpensesEntryModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import java.lang.Exception

class AdminView : View("Admin Staff") {

    var model = AdminEntryModel()
    val controller: AdminController by inject()

    var mTableView: TableViewEditModel<AdminEntryModel> by singleAssign()
    var totalSalariesLabel: Label by singleAssign()
    val totalSalariesProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalSalaries()
    }

    override val root = borderpane {
        center = vbox {
            form {
                fieldset {
                    field("Name") {
                        maxWidth = 220.0
                        textfield(model.adminName) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                    }
                }
                fieldset {
                    field("Surname") {
                        maxWidth = 220.0
                        textfield(model.adminSurname) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                fieldset {
                    field("Student Number") {
                        maxWidth = 220.0
                        textfield(model.adminRole) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                    }
                }
                fieldset {
                    field("Salary") {
                        maxWidth = 220.0
                        textfield(model.adminSalary) {
                            this.required()
                            validator {
                                when(it) {
                                    null -> error("The price cannot be blank")
                                    else -> null
                                }
                            }

                            setOnKeyPressed {
                                if (it.code == KeyCode.ENTER) {
                                    model.commit {
                                        addItem()
                                        model.rollback()
                                    }
                                }
                            }
                        }
                    }
                }

                hbox(10.0) {
                    button("Add Item") {
                        enableWhen(model.valid)
                        action{
                            model.commit{
                                addItem()
                                model.rollback()
                            }

                        }
                    }

                    button("delete"){
                        action {
                            val selectedItem: AdminEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalSalariesProperty.value
                                    - selectedItem.item.adminSalary
                                    totalSalariesProperty.value = diff

                                    controller.delete(selectedItem)
                                    updateTotalSalaries()
                                }
                            }
                            controller.delete(selectedItem!!)
                        }
                    }

                }
                fieldset {
                    tableview<AdminEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", AdminEntryModel::adminId)
                        column("Name", AdminEntryModel::adminName).makeEditable()
                        column("Surname", AdminEntryModel::adminSurname).makeEditable()
                        column("Role", AdminEntryModel::adminRole).makeEditable()

                        onEditCommit {
                            controller.update(it)
                        }
                    }
                }


            }
        }

        right = vbox {
            alignment = Pos.CENTER

//            piechart("Total Expenses") {
//                data = controller.pieItemsData
//            }

            totalSalariesLabel = label {
                if (totalSalariesProperty.doubleValue() != 0.0) {
                    bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalSalariesProperty)))
                } else {

                }
            }
        }
    }

    private fun updateTotalSalaries() {
        var total = 0.0
        try {
            controller.items.forEach {
                total += it.adminSalary.value.toDouble()
            }
            totalSalariesProperty.set(total)
            model.totalAdminSalaryExpenses.value = total

        } catch (e:Exception) {
            totalSalariesProperty.set(0.0)
        }
    }

    private fun addItem() {
        controller.add(model.adminName.value, model.adminSurname.value, model.adminRole.value, model.adminSalary.value.toDouble())

        updateTotalSalaries()
    }
}