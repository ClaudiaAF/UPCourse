package com.example.demo.view

import com.example.demo.controller.DegreeStudentController
import com.example.demo.controller.DiplomaStudentController
import com.example.demo.controller.SubjectsController
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.DiplomaStudentsEntryModel
import com.example.demo.model.ExpensesEntryModel
import com.example.demo.model.SubjectsEntryModel
import com.example.demo.util.Searchable
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.control.TextInputControl
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import org.slf4j.MDC.clear
import tornadofx.*
import java.lang.Exception

class DiplomaStudentsView : View("Diploma Students"), Searchable {

    var model = DiplomaStudentsEntryModel()
    val controller: DiplomaStudentController by inject()

    val subjectController: SubjectsController by inject()
    val boxObject = SimpleObjectProperty<SubjectsEntryModel>()

    var mTableView: TableViewEditModel<DiplomaStudentsEntryModel> by singleAssign()
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
                        textfield(model.diplomaStudentName) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                        if(model.commit()){
                           textfield().clear()
                        } else {
                            null
                        }
                    }
                }
                fieldset {
                    field("Surname") {
                        maxWidth = 220.0
                        textfield(model.diplomaStudentSurname) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                        if(model.commit()){
                            textfield().clear()
                        } else {
                            null
                        }
                    }
                }

                fieldset {
                    field {
                        text = "Subject"
                        combobox<SubjectsEntryModel>(boxObject, values = subjectController.listOfSubjects) {
                            cellFormat {
                                bind(model.diplomaStudentSubject)
                                text = this.item.subjectName.value
//                                if (item.subjectCredits < 20) {
//
//                                        error("Please choose a Diploma Subject")
//
//                                } else {
//                                    //do nothing
//                                }
                            }
                        }
                        if(model.commit()){
                            textfield().clear()
                        } else {
                            null
                        }
                    }
                }

                fieldset {
                    field("Student Number") {
                        maxWidth = 220.0
                        textfield(model.diplomaStudentNumber) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                        }
                        if(model.commit()){
                            this.textfield().clear()
                        } else {
                            null
                        }
                    }
                }
                fieldset {
                    field("Fees") {
                        maxWidth = 220.0
                        textfield(model.diplomaStudentFees) {
                            setText("350")
                            isEditable = false
                        }
                    }
                }

                hbox(10.0) {
                    button("Add Item") {
                        enableWhen(model.valid)
                        action{
                            model.commit{
                                addItem()
//                                model.rollback()
                            }

                            setOnKeyPressed {
                                if (it.code == KeyCode.ENTER) {
                                    model.commit {
                                        addItem()
//                                        model.rollback()
                                    }
                                }
                            }

                        }
                    }

                    button("delete"){
                        action {
                            val selectedItem: DiplomaStudentsEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalFeesProperty.value
                                    - selectedItem.item.diplomaStudentFees

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
                    tableview<DiplomaStudentsEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", DiplomaStudentsEntryModel::diplomaStudentId)
                        column("Name", DiplomaStudentsEntryModel::diplomaStudentName).makeEditable()
                        column("Surname", DiplomaStudentsEntryModel::diplomaStudentSurname).makeEditable()
                        column("Subject", DiplomaStudentsEntryModel::diplomaStudentSubject)
                        column("Student Number",DiplomaStudentsEntryModel::diplomaStudentNumber).makeEditable()

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
                total += it.diplomaStudentFees.value.toDouble()
            }
            totalFeesProperty.set(total)
            model.totalFeesDiplomaExpenses.value = total

        } catch (e:Exception) {
            totalFeesProperty.set(0.0)
        }
    }

    fun TextInputControl.filterInput(discriminator: (TextFormatter.Change) -> Boolean) {
        textFormatter = TextFormatter<Any>(CustomTextFilter(discriminator))
    }

    private fun addItem() {
        controller.add(model.diplomaStudentName.value, model.diplomaStudentSurname.value, model.diplomaStudentSubject.value, model.diplomaStudentNumber.value, model.diplomaStudentFees.value.toDouble())

        updateTotalFees()
    }

    override fun onSearch(query: String) {
        println("Searching for $query...")
    }
}