package com.example.demo.view

import com.example.demo.controller.ItemController
import com.example.demo.model.ExpensesEntryModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import tornadofx.*
import java.awt.TextField
import java.time.LocalDate
import javax.swing.text.TableView

class EditView : View("Person Editor") {

    var model = ExpensesEntryModel()
    val controller: ItemController by inject()

    var mTableView: TableViewEditModel<ExpensesEntryModel> by singleAssign()
    var totalExpensesLabel: Label by singleAssign()
    val totalExpensesProperty = SimpleDoubleProperty(0.0)

//    init {
//        updateTotalExpenses()
//    }

    override val root = borderpane {

        center = vbox {
            form {
                fieldset {
                    field("Entry Date") {
                        maxWidth = 220.0
                        textfield(model.entryDate) {
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
                    field("Item") {
                        maxWidth = 220.0
                        textfield(model.itemName) {
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
                    field("Price") {
                        maxWidth = 220.0
                        textfield(model.itemPrice) {
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
                        val selectedItem: ExpensesEntryModel? = mTableView.tableView.selectedItem
                            when(selectedItem) {
                                null -> return@action
                                else -> {
                                    val diff = totalExpensesProperty.value
                                    - selectedItem.item.itemPrice
                                    totalExpensesProperty.value = diff

                                    controller.delete(selectedItem)
//                                    updateTotalExpenses()
                                }
                            }
                            controller.delete(selectedItem!!)
                        }
                    }

                    button("Reset"){
                        action {

                        }
                    }

                }
                fieldset {
                    tableview<ExpensesEntryModel> {
                        items = controller.items
                        mTableView = editModel
                        column("ID", ExpensesEntryModel::id)
                        column("Added", ExpensesEntryModel::entryDate).makeEditable()
                        column("Name", ExpensesEntryModel::itemName).makeEditable()
                        column("Price", ExpensesEntryModel::itemPrice).makeEditable()

                        onEditCommit {
                            controller.update(it)
                            controller.updatePiecePie(it)
                        }
                    }
                }


            }
        }

        right = vbox {
            alignment = Pos.CENTER

            piechart("Total Expenses") {
                data = controller.pieItemsData
            }

//            totalExpensesLabel = label {
//                if (totalExpensesProperty.doubleValue() != 0.0) {
//                    bind(Bindings.concat("Total expenses: ", "$", Bindings.format("%.2f", totalExpensesProperty)))
//                } else {
//
//                }
//            }
        }
    }

//    private fun updateTotalExpenses() {
//        var total = 0.0
//        try {
//
//            controller.items.forEach {
//                total += it.itemPrice.value.toDouble()
//            }
//            totalExpensesProperty.set(total)
//            model.totalExpenses.value = total
//
//        } catch (e:Exception) {
//            totalExpensesProperty.set(0.0)
//        }
//
//
//    }
    private fun addItem() {
        controller.add(model.entryDate.value, model.itemName.value, model.itemPrice.value.toDouble())

    }
}
