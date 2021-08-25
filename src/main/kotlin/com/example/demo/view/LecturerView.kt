package com.example.demo.view

import com.example.demo.controller.LecturerController
import com.example.demo.controller.SubjectsController
import com.example.demo.model.LecturerEntryModel
import com.example.demo.model.LecturerStaffTbl.lecturerSubject
import com.example.demo.model.SubjectsEntryModel
import com.example.demo.util.Searchable
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import tornadofx.*
import tornadofx.controlsfx.columnfilter
import tornadofx.controlsfx.exceptValue
import java.lang.Exception
import javax.swing.ComboBoxModel

class LecturerView : View("Lecturer Staff"), Searchable {

    var model = LecturerEntryModel()
    var subjectModel = SubjectsEntryModel()
    val controller: LecturerController by inject()
    val subjectController: SubjectsController by inject()
    val boxObject = SimpleObjectProperty<SubjectsEntryModel>()


    var mTableView: TableViewEditModel<LecturerEntryModel> by singleAssign()
    var totalSalariesLabel: Label by singleAssign()
    val totalSalariesProperty = SimpleDoubleProperty(0.0)

    init {
        updateTotalSalaries()
        subjectsList()

    }

    override val root = borderpane {

        center = vbox {
            vboxConstraints {
                paddingTop = 30.0
                paddingLeft = 80.0
            }
            label("Leturers") {
                style {
                    fontFamily = "Open Sans"
                    fontSize = 40.pt
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 50
                }
            }

            form {
                fieldset {
                    field("Name") {
                        maxWidth = 400.0
                        textfield(model.lecturerName) {
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
                        textfield(model.lecturerSurname) {
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
                        text = "Subject Taught"
                        combobox<SubjectsEntryModel>(boxObject, values = subjectController.listOfSubjects) {
                            cellFormat {
                                text = this.item.subjectName.value
                                bind(model.lecturerSubject)
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
                        maxWidth = 220.0
                        textfield(model.lecturerSalary) {
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
                            val selectedItem: LecturerEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalSalariesProperty.value
                                    - selectedItem.item.lecturerSalary
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

            tableview<LecturerEntryModel> {
                addClass(Styles.regularTable)

                items = controller.items
                mTableView = editModel
                column("ID", LecturerEntryModel::lecturerId)
                column("Name", LecturerEntryModel::lecturerName).makeEditable()
                column("Surname", LecturerEntryModel::lecturerSurname).makeEditable()
                column("Subject Taught", LecturerEntryModel::lecturerSubject)

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

    private fun subjectsList() {
        val subjects = String()
        subjectModel.subjectName.value = subjects
    }

    private fun updateTotalSalaries() {
        var total = 0.0

        try {
            controller.items.forEach {
                total += it.lecturerSalary.value.toDouble()
            }

            totalSalariesProperty.set(total)
            model.totalLecturerSalaryExpenses.value = total

        } catch (e:Exception) {
            totalSalariesProperty.set(0.0)
        }
    }

    private fun addItem() {
        controller.add(model.lecturerName.value, model.lecturerSurname.value, model.lecturerSubject.value, model.lecturerSalary.value.toDouble())

        updateTotalSalaries()
    }

    val searchResult = mutableListOf<LecturerEntryModel>().observable()

    override fun onSearch(query: String) {

        println("Searching for $query...")

    }
}