package com.example.demo.view

import com.example.demo.controller.AdminController
import com.example.demo.controller.DegreeStudentController
import com.example.demo.model.AdminEntryModel
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.ExpensesEntryModel
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
import java.net.URI

class AdminView : View("Admin Staff"), Searchable {

    var model = AdminEntryModel()
    val controller: AdminController by inject()

    var mTableView: TableViewEditModel<AdminEntryModel> by singleAssign()
    var totalSalariesLabel: Label by singleAssign()
    val totalSalariesProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalSalaries()
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

                label("Admin Staff"){
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
                        textfield(model.adminName) {
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
                        textfield(model.adminSurname) {
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
                    field("Role") {
                        maxWidth = 400.0
                        textfield(model.adminRole) {
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
                    field("Salary") {
                        maxWidth = 300.0
                        textfield(model.adminSalary) {
                            setText("15 000")
                            isEditable = false

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
//                                        model.rollback()
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
//                                model.rollback()
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
                spacing = 20.0
            }
        }

        right = vbox {
            vboxConstraints {
                alignment = Pos.CENTER_RIGHT
                paddingRight = 160.0
            }

            tableview<AdminEntryModel> {
                addClass(Styles.regularTable)

                items = controller.items
                mTableView = editModel
                column("ID", AdminEntryModel::adminId)
                column("Name", AdminEntryModel::adminName).makeEditable()
                column("Surname", AdminEntryModel::adminSurname).makeEditable()
                column("Role", AdminEntryModel::adminRole).makeEditable()

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
                totalSalariesLabel = label {
                    if (totalSalariesProperty.doubleValue() != 0.0) {
                        bind(Bindings.concat("Total expenses: ", "R", Bindings.format("%.2f", totalSalariesProperty)))
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

    override fun onSearch(query: String) {
        println("Searching for $query...")
    }
}