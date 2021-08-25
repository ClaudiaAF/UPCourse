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

            form {
                fieldset {
                    field("Name") {
                        maxWidth = 220.0
                        textfield(model.lecturerName) {
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
                        textfield(model.lecturerSurname) {
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
                        textfield(model.lecturerSalary) {
                            setText("350")
                            isEditable = false

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
                }

                combobox<SubjectsEntryModel>(boxObject, values = subjectController.listOfSubjects) {

                    cellFormat {
                        text = this.item.subjectName.value
                        bind(model.lecturerSubject)
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

                        }
                    }

                    button("delete"){
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
                fieldset {
                    togglebutton ("Subjects"){
                        action {
                            text = if (isSelected) "On" else "off"
                        }
                    }
                    tableview<LecturerEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", LecturerEntryModel::lecturerId)
                        column("Name", LecturerEntryModel::lecturerName).makeEditable()
                        column("Surname", LecturerEntryModel::lecturerSurname).makeEditable()
                        column("Subject Taught", LecturerEntryModel::lecturerSubject)

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