package com.example.demo.view

import com.example.demo.controller.AdminController
import com.example.demo.controller.DegreeStudentController
import com.example.demo.controller.SubjectsController
import com.example.demo.model.AdminEntryModel
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.ExpensesEntryModel
import com.example.demo.model.SubjectsEntryModel
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

class SubjectsView : View("Subjects") {

    var model = SubjectsEntryModel()
    val controller: SubjectsController by inject()

    var mTableView: TableViewEditModel<SubjectsEntryModel> by singleAssign()
    var totalPriceLabel: Label by singleAssign()
    val totalPriceProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalPrice()
    }

    override val root = borderpane {
        center = vbox {
            form {
                fieldset {
                    field("Subject Name") {
                        maxWidth = 220.0
                        textfield(model.subjectName) {
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
                    field("Code") {
                        maxWidth = 220.0
                        textfield(model.subjectCode) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length > 2 -> error("Must be 2 letters")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                fieldset {
                    field("Credits") {
                        maxWidth = 220.0
                        textfield(model.subjectCredits) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length > 2 -> error("Must be 2 Numbers")
                                    else -> null
                                }
                            }
                        }
                    }
                }
                fieldset {
                    field("Hours per Week") {
                        maxWidth = 220.0
                        textfield(model.hoursPerWeek) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 2 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                    }
                }


                    fieldset {
                        field("Price Per Month") {
                            maxWidth = 220.0
                            textfield(model.pricePerMonth) {
                                this.required()
                                validator {
                                    when {
                                        it.isNullOrEmpty() -> error("field cannot be empty")
                                        it!!.length < 3 -> error("too short")
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
                            val selectedItem: SubjectsEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalPriceProperty.value
                                    - selectedItem.item.pricePerMonth
                                    totalPriceProperty.value = diff

                                    controller.delete(selectedItem)
                                    updateTotalPrice()
                                }
                            }
                            controller.delete(selectedItem!!)
                        }
                    }

                }
                fieldset {
                    tableview<SubjectsEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", SubjectsEntryModel::subjectId)
                        column("Subject Name", SubjectsEntryModel::subjectName).makeEditable()
                        column("Code", SubjectsEntryModel::subjectCode).makeEditable()
                        column("Credits", SubjectsEntryModel::subjectCredits).makeEditable()
                        column("Hours Per Week", SubjectsEntryModel::hoursPerWeek).makeEditable()

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

            totalPriceLabel = label {
                if (totalPriceProperty.doubleValue() != 0.0) {
                    bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalPriceProperty)))
                } else {

                }
            }
        }
    }

    private fun updateTotalPrice() {
        var total = 0.0
        try {
            controller.items.forEach {
                total += it.totalPricePerMonthExpenses.value.toDouble()
            }
            totalPriceProperty.set(total)
            model.totalPricePerMonthExpenses.value = total

        } catch (e:Exception) {
            totalPriceProperty.set(0.0)
        }
    }

    private fun addItem() {
        controller.add(model.subjectName.value, model.subjectCode.value, model.subjectCredits.value, model.hoursPerWeek.value, model.pricePerMonth.value.toDouble())

        updateTotalPrice()
    }
}