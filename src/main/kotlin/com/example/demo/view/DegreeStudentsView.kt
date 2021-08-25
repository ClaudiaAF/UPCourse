package com.example.demo.view

import com.example.demo.controller.DegreeStudentController
import com.example.demo.controller.SubjectsController
import com.example.demo.model.DegreeStudentsEntryModel
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
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import java.lang.Exception

class DegreeStudentsView : View("Degree Students"), Searchable {

    var model = DegreeStudentsEntryModel()
    val controller: DegreeStudentController by inject()

    val subjectController: SubjectsController by inject()
    val boxObject = SimpleObjectProperty<SubjectsEntryModel>()

    var mTableView: TableViewEditModel<DegreeStudentsEntryModel> by singleAssign()
    var totalFeesLabel: Label by singleAssign()
    val totalFeesProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalFees()
    }

    override val root = borderpane {
        center = vbox {
            vboxConstraints {
                paddingTop = 30.0
                paddingLeft = 80.0
            }
            label("Degree Students"){
                style {
                    fontFamily = "Open Sans"
                    fontSize = 40.pt
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 50
                }
            }

            form {
                fieldset() {

                    field("Name") {
                        maxWidth = 400.0
                        textfield(model.degreeStudentName) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                            style {
                                padding = box(12.px)
                                backgroundRadius += box(10.px)
                                borderRadius += box(10.px)
                                borderColor += box(Color.TRANSPARENT)
                                backgroundColor += Color.WHITE
                            }
                        }
                        style {
                            fontFamily = "Open Sans"
                            fontSize = 10.pt
                            fontWeight = FontWeight.LIGHT
                        }
                    }
                }
                fieldset {
                    field("Surname") {
                        maxWidth = 400.0
                        textfield(model.degreeStudentSurname) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("field cannot be empty")
                                    it!!.length < 3 -> error("too short")
                                    else -> null
                                }
                            }
                            style {
                                padding = box(12.px)
                                backgroundRadius += box(10.px)
                                borderRadius += box(10.px)
                                borderColor += box(Color.TRANSPARENT)
                                backgroundColor += Color.WHITE
                            }
                        }
                        style {
                            fontFamily = "Open Sans"
                            fontSize = 10.pt
                            fontWeight = FontWeight.MEDIUM
                        }

                    }
                }

                    fieldset {
                        field {
                            text = "Subject"
                            combobox<SubjectsEntryModel>(boxObject, values = subjectController.listOfSubjects) {
                                cellFormat {
                                    text = this.item.subjectName.value
                                    bind(model.degreeStudentSubject)
//                                    if (item.subjectCredits > 20) {
//
//                                        error("Please choose a Degree Subject")
//
//                                    } else {
//                                        //do nothing
//                                    }
                                }
                                style {
                                    padding = box(12.px)
                                    backgroundRadius += box(10.px)
                                    borderRadius += box(10.px)
                                    borderColor += box(Color.TRANSPARENT)
                                    backgroundColor += Color.WHITE
                                }
                            }
                            style {
                                fontFamily = "Open Sans"
                                fontSize = 10.pt
                                fontWeight = FontWeight.LIGHT
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
                            style {
                                padding = box(12.px)
                                backgroundRadius += box(10.px)
                                borderRadius += box(10.px)
                                borderColor += box(Color.TRANSPARENT)
                                backgroundColor += Color.WHITE
                            }
                        }
                        style {
                            fontFamily = "Open Sans"
                            fontSize = 10.pt
                            fontWeight = FontWeight.LIGHT
                        }

                    }
                }

                fieldset {
                    field("Fees") {
                        maxWidth = 220.0
                        textfield(model.degreeStudentFees) {
                            setText("350")
                            isEditable = false


                            style {
                                padding = box(12.px)
                                backgroundRadius += box(10.px)
                                borderRadius += box(10.px)
                                borderColor += box(Color.TRANSPARENT)
                                backgroundColor += Color.WHITE
                            }
                        }
                        style {
                            fontFamily = "Open Sans"
                            fontSize = 10.pt
                            fontWeight = FontWeight.LIGHT
                        }

                    }
                }

                hbox(10.0) {
                    button("Add Item") {

                        style{
                            backgroundColor = multi(Styles.borderLineColor, Styles.borderLineColor, Styles.borderLineColor)
                            textFill = Color.WHITE
                            fontFamily = "Open Sans"
                            fontWeight = FontWeight.BOLD
                            backgroundRadius += box(10.px)
                            padding = box(15.px, 50.px)
                        }

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

                        style{
                            backgroundColor = multi(Styles.bloodRed, Styles.bloodRed, Styles.bloodRed)
                            textFill = Color.WHITE
                            fontFamily = "Open Sans"
                            fontWeight = FontWeight.BOLD
                            backgroundRadius += box(10.px)
                            padding = box(15.px, 50.px)
                        }

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

                spacing = 40.0
            }
        }

        right = vbox {
            vboxConstraints {
                alignment = Pos.CENTER_RIGHT
                paddingRight = 160.0
            }

            tableview<DegreeStudentsEntryModel> {

                addClass(Styles.regularTable)
                items = controller.items
                mTableView = editModel
                column("ID", DegreeStudentsEntryModel::degreeStudentId)
                column("Name", DegreeStudentsEntryModel::degreeStudentName).makeEditable()
                column("Surname", DegreeStudentsEntryModel::degreeStudentSurname).makeEditable()
                column("Subject", DegreeStudentsEntryModel::degreeStudentSubject).makeEditable()
                column("Student Number", DegreeStudentsEntryModel::degreeStudentNumber).makeEditable()

                onEditCommit {
                    controller.update(it)
                }
                style{
                    prefWidth = 1000.px
                }
            }

            stackpane {
                stackpaneConstraints {
                    paddingTop = 30.0
                    alignment = Pos.CENTER_RIGHT
                }
                rectangle {
                    width = 500.0
                    height = 150.0
                    arcHeight = 100.0
                    arcWidth = 100.0
                    fill = Styles.borderLineColor
                }
                totalFeesLabel = label {
                    if (totalFeesProperty.doubleValue() != 0.0) {
                        bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalFeesProperty)))
                    } else {
                        //do nothing
                    }
                    style {
                        fontFamily = "Open Sans"
                        fontSize = 25.pt
                        fontWeight = FontWeight.BOLD
                    }
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
        controller.add(model.degreeStudentName.value, model.degreeStudentSurname.value, model.degreeStudentSubject.value, model.degreeStudentNumber.value, model.degreeStudentFees.value.toDouble())

        updateTotalFees()
    }

    override fun onSearch(query: String) {
        println("Searching for $query...")
    }
}