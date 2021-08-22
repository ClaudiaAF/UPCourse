package com.example.demo.view

import com.example.demo.controller.DegreeStudentController
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

class DegreeStudentsView : View("Degree Students") {

    var model = DegreeStudentsEntryModel()
    val controller: DegreeStudentController by inject()

    var mTableView: TableViewEditModel<DegreeStudentsEntryModel> by singleAssign()
    var totalFeesLabel: Label by singleAssign()
    val totalFeesProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalFees()
    }

    override val root = borderpane {
        center = vbox {
            form {
                fieldset {
                    field("Name") {
                        maxWidth = 220.0
                        textfield(model.degreeStudentName) {
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
                        textfield(model.degreeStudentSurname) {
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
                        textfield(model.degreeStudentNumber) {
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
                    field("Fees") {
                        maxWidth = 220.0
                        textfield(model.degreeStudentFees) {
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
                            val selectedItem: DegreeStudentsEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalFeesProperty.value
                                    - selectedItem.item.degreeStudentFees
                                    totalFeesProperty.value = diff

                                    controller.delete(selectedItem)
                                    updateTotalFees()
                                }
                            }
                            controller.delete(selectedItem!!)
                        }
                    }

                }
                fieldset {
                    tableview<DegreeStudentsEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", DegreeStudentsEntryModel::degreeStudentId)
                        column("Name", DegreeStudentsEntryModel::degreeStudentName).makeEditable()
                        column("Surname", DegreeStudentsEntryModel::degreeStudentSurname).makeEditable()
                        column("Student Number", DegreeStudentsEntryModel::degreeStudentNumber).makeEditable()

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

            totalFeesLabel = label {
                if (totalFeesProperty.doubleValue() != 0.0) {
                    bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalFeesProperty)))
                } else {

                }
            }
        }
    }

    private fun updateTotalFees() {
        var total = 0.0
        try {
            controller.items.forEach {
                total += it.degreeStudentFees.value.toDouble()
            }
            totalFeesProperty.set(total)
            model.totalFeesExpenses.value = total

        } catch (e:Exception) {
            totalFeesProperty.set(0.0)
        }
    }

    private fun addItem() {
        controller.add(model.degreeStudentName.value, model.degreeStudentSurname.value, model.degreeStudentNumber.value, model.degreeStudentFees.value.toDouble())

        updateTotalFees()
    }
}