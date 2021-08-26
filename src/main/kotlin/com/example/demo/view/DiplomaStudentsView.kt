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
import javafx.scene.text.FontWeight
import org.slf4j.MDC.clear
import tornadofx.*
import java.lang.Exception
import java.net.URI

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
        style{
            backgroundImage += URI("https://drive.google.com/uc?export=view&id=1Qbgijg9Er2K8YTeyBL2zHU_TMgOID_N0")
        }
        center = vbox {
            vboxConstraints {
                paddingTop = 30.0
                paddingLeft = 80.0
            }
            label("Diploma Students"){
                style {
                    fontFamily = "Open Sans"
                    fontSize = 40.pt
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 50
                    paddingTop = 50
                }
            }

            form {
                fieldset {
                    field("Name") {
                        maxWidth = 400.0
                        textfield(model.diplomaStudentName) {
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

                        if(model.commit()){
                           textfield().clear()
                        } else {
                            null
                        }
                    }
                }
                fieldset {
                    field("Surname") {
                        maxWidth = 400.0
                        textfield(model.diplomaStudentSurname) {
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
                                if (item.subjectCredits >60) {

                                        error("Please choose a Diploma Subject")

                                } else {
                                    //do nothing
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
                spacing = 20.0
            }
        }

        right = vbox {
            vboxConstraints {
                alignment = Pos.CENTER_RIGHT
                paddingRight = 160.0
            }

            tableview<DiplomaStudentsEntryModel> {
                addClass(Styles.regularTable)

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