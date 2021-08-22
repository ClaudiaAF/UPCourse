package com.example.demo.view

import com.example.demo.controller.LecturerController
import com.example.demo.model.LecturerEntryModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import tornadofx.*
import java.lang.Exception

class LecturerView : View("Lecturer Staff") {

    var model = LecturerEntryModel()
    val controller: LecturerController by inject()

    var mTableView: TableViewEditModel<LecturerEntryModel> by singleAssign()
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
                    field("Subject Taught") {
                        maxWidth = 220.0
                        textfield(model.lecturerSubject) {
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
                    tableview<LecturerEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", LecturerEntryModel::lecturerId)
                        column("Name", LecturerEntryModel::lecturerName).makeEditable()
                        column("Surname", LecturerEntryModel::lecturerSurname).makeEditable()
                        column("Subject Taught", LecturerEntryModel::lecturerSubject).makeEditable()

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
}