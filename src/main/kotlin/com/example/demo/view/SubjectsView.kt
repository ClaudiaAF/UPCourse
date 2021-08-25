package com.example.demo.view

import com.example.demo.controller.AdminController
import com.example.demo.controller.DegreeStudentController
import com.example.demo.controller.SubjectsController
import com.example.demo.model.AdminEntryModel
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.ExpensesEntryModel
import com.example.demo.model.SubjectsEntryModel
import com.example.demo.util.Searchable
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
import javafx.scene.text.FontWeight
import tornadofx.*
import java.lang.Exception

class SubjectsView : View("Subjects"), Searchable {

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
            vboxConstraints {
                paddingTop = 30.0
                paddingLeft = 80.0
            }
            label("Subjects"){
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
                    field("Subject Name") {
                        maxWidth = 400.0
                        textfield(model.subjectName) {
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
                    field("Hours/Week") {
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
                        field("Price/Month") {
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
                                style {
                                    padding = box(12.px)
                                    backgroundRadius += box(10.px)
                                    borderRadius += box(10.px)
                                    borderColor += box(Color.TRANSPARENT)
                                    backgroundColor += Color.WHITE
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
                                model.rollback()
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
                spacing = 20.0
            }
        }

        right = vbox {
            vboxConstraints {
                alignment = Pos.CENTER_RIGHT
                paddingRight = 160.0
            }

            tableview<SubjectsEntryModel> {
                addClass(Styles.regularTable)

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
                totalPriceLabel = label {
                    if (totalPriceProperty.doubleValue() != 0.0) {
                        bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalPriceProperty)))
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

    override fun onSearch(query: String) {
        println("Searching for $query...")
    }
}