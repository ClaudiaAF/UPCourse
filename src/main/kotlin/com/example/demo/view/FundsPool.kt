package com.example.demo.view

import com.example.demo.controller.*
import com.example.demo.model.*
import com.sun.javafx.binding.ContentBinding.bind
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import java.lang.Exception
import java.net.URI
import javax.xml.soap.Text

class FundsPool : View ("Funds Pool") {

    var overallExpense = SimpleDoubleProperty(400000.00)

    //lecturers
    val lecturersController: LecturerController by inject()
    var lecturerModel = LecturerEntryModel()
    var lecturerSalariesLabel: Label by singleAssign()
    val lecturerSalariesProperty = SimpleDoubleProperty(0.0)

    //subjects
    var subjectsModel = SubjectsEntryModel()
    val subjectsController: SubjectsController by inject()
    var subjectsPriceLabel: Label by singleAssign()
    val subjectsPriceProperty = SimpleDoubleProperty(0.0)

    //diploma students
    var diplomaModel = DiplomaStudentsEntryModel()
    val diplomaController: DiplomaStudentController by inject()
    var diplomaFeesLabel: Label by singleAssign()
    val diplomaFeesProperty = SimpleDoubleProperty(0.0)

    //degree students
    var degreeModel = DegreeStudentsEntryModel()
    val degreeController: DegreeStudentController by inject()
    var degreeFeesLabel: Label by singleAssign()
    val degreeFeesProperty = SimpleDoubleProperty(0.0)

    //admin salaries
    var adminModel = AdminEntryModel()
    val adminController: AdminController by inject()
    var adminSalariesLabel: Label by singleAssign()
    val adminSalariesProperty = SimpleDoubleProperty(0.0)
    var totalCostNumber = SimpleStringProperty()

    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        lecturerTotalSalaries()
        subjectsTotalPrice()
        diplomaTotalFees()
        degreeTotalFees()
        adminTotalSalaries()

    }

    override val root = borderpane {
        style{
            backgroundImage += URI("https://drive.google.com/uc?export=view&id=1lPUw_VVJTSkvIkKu5m1GJn-_0fS8YVif")
        }

        top = vbox {
            vboxConstraints {
                paddingTop = 30.0
                paddingLeft = 80.0
                alignment = Pos.CENTER_LEFT
            }
            label("Funds Pool"){
                style {
                    fontFamily = "Open Sans"
                    fontSize = 40.pt
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 20
                    paddingTop = 50
                    paddingLeft = 50
                }
            }

            hbox {
                hboxConstraints {
                    alignment = Pos.CENTER
                }
                stackpane {
                    stackpaneConstraints {
                        paddingTop = 5.0
                        paddingBottom = 40.0
                    }
                    rectangle {
                        width = 400.0
                        height = 100.0
                        arcHeight = 100.0
                        arcWidth = 100.0
                        fill = Styles.borderLineColor
                    }
                    text {
                        if (overallExpense.doubleValue() != 0.0) {
                            textProperty().bind(Bindings.concat("R", Bindings.format("%.2f", overallExpense)))
                        } else {
                            //do nothing
                        }
                        style {
                            alignment = Pos.CENTER
                            fontFamily = "Open Sans"
                            fontSize = 30.pt
                            fontWeight = FontWeight.BOLD
                            textFill = Color.WHITE
                        }
                    }
                }

            }
            hbox {
                hboxConstraints {
                    alignment = Pos.CENTER

                }
                piechart("Total Expenses") {
                    data("Lecturers Expenses", lecturerSalariesProperty.value.toDouble())
                    data("Admin Expenses", adminSalariesProperty.value.toDouble())

                    style {
                        fontFamily = "Open Sans"
                        fontSize = 10.pt
                        fontWeight = FontWeight.MEDIUM
                    }
                }
               rectangle {
                   width = 180.0
                   height = 1.0
                   fill = Color.TRANSPARENT
               }

                piechart("Total Owed To UPCourse") {
                    data("Subjects Total Owed", subjectsPriceProperty.value.toDouble())
                    data("Diploma Fees Owed", diplomaFeesProperty.value.toDouble())
                    data("Degree Fees Owed", degreeFeesProperty.value.toDouble())

                    style {
                        fontFamily = "Open Sans"
                        fontSize = 10.pt
                        fontWeight = FontWeight.MEDIUM
                    }
                }
            }

            hbox (spacing = 30, alignment = Pos.CENTER_LEFT){

                lecturerSalariesLabel = label {
                    if (lecturerSalariesProperty.doubleValue() != 0.0) {
                        bind(
                            Bindings.concat(
                                " ",
                                "R",
                                Bindings.format("%.2f", lecturerSalariesProperty)
                            )
                        )
                    } else {

                    }
                    paddingTop = 10
                    paddingLeft = 440
                    paddingBottom = 10

                    style {
                        fontFamily = "Open Sans"
                        fontSize = 15.pt
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.bloodOrange
                    }
                }

                adminSalariesLabel = label {
                    if (adminSalariesProperty.doubleValue() != 0.0) {
                        bind(
                            Bindings.concat(
                                " ",
                                "R",
                                Bindings.format("%.2f", adminSalariesProperty)
                            )
                        )
                    } else {

                    }
                    paddingTop = 10
                    paddingLeft = 20
                    paddingBottom = 10

                    style {
                        fontFamily = "Open Sans"
                        fontSize = 15.pt
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.borderLineColor
                    }
                }
                //here?
                rectangle {
                    width = 257.0
                    height = 1.0
                    fill = Color.TRANSPARENT
                }

                subjectsPriceLabel = label {
                    if (subjectsPriceProperty.doubleValue() != 0.0) {
                        bind(
                            Bindings.concat(
                                " ",
                                "R",
                                Bindings.format("%.2f", subjectsPriceProperty)
                            )
                        )
                    } else {

                    }
                    paddingTop = 10
                    paddingLeft = 80
                    paddingBottom = 10
                    style {
                        fontFamily = "Open Sans"
                        fontSize = 15.pt
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.bloodOrange
                    }
                }

                //diploma here
                diplomaFeesLabel = label {
                    if (diplomaFeesProperty.doubleValue() != 0.0) {
                        bind(Bindings.concat(" ", "R", Bindings.format("%.2f", diplomaFeesProperty)))
                    } else {

                    }
                    paddingTop = 10
                    paddingBottom = 10
                    style {
                        fontFamily = "Open Sans"
                        fontSize = 15.pt
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.borderLineColor
                    }
                }

                //degree here
                degreeFeesLabel = label {
                    if (degreeFeesProperty.doubleValue() != 0.0) {
                        bind(Bindings.concat(" ", "R", Bindings.format("%.2f", degreeFeesProperty)))
                    } else {

                    }
                    paddingTop = 10
                    paddingBottom = 10
                    style {
                        fontFamily = "Open Sans"
                        fontSize = 15.pt
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.greenPastures
                    }
                }
            }

                    hbox {

                        rectangle {
                            width = 457.0
                            height = 1.0
                            fill = Color.TRANSPARENT
                        }

                        val addedExpenses = lecturerSalariesProperty.value.toDouble() + adminSalariesProperty.value.toDouble()

                        button("Settle Payments") {
                            style {
                                backgroundColor = multi(Styles.borderLineColor, Styles.borderLineColor, Styles.borderLineColor)
                                textFill = Color.WHITE
                                fontFamily = "Open Sans"
                                fontWeight = FontWeight.BOLD
                                backgroundRadius += box(10.px)
                                padding = box(15.px, 50.px)
                            }

                            action {
                                val diff = overallExpense.value.toDouble() - addedExpenses

                                overallExpense.value = diff
                            }
                        }

                        rectangle {
                            width = 460.0
                            height = 1.0
                            fill = Color.TRANSPARENT
                        }

                        val addedPayments = subjectsPriceProperty.value.toDouble() + diplomaFeesProperty.value.toDouble() + degreeFeesProperty.value.toDouble()
                        button("Settle Incomming Payments"){
                            style{
                                backgroundColor = multi(Styles.borderLineColor, Styles.borderLineColor, Styles.borderLineColor)
                                textFill = Color.WHITE
                                fontFamily = "Open Sans"
                                fontWeight = FontWeight.BOLD
                                backgroundRadius += box(10.px)
                                padding = box(15.px, 50.px)
                            }

                            action {
                                val diff = overallExpense.value.toDouble() + addedPayments

                                overallExpense.value = diff
                            }
                        }
                    }
                }
             }


    private fun subjectsTotalPrice() {
        var total = 0.0
        try {
            subjectsController.items.forEach {
                total += it.totalPricePerMonthExpenses.value.toDouble()
            }
            subjectsPriceProperty.set(total)
            subjectsModel.totalPricePerMonthExpenses.value = total

        } catch (e:Exception) {
            subjectsPriceProperty.set(0.0)
        }
    }


    private fun lecturerTotalSalaries() {
        var total = 0.0
        try {
            lecturersController.items.forEach {
                total += it.lecturerSalary.value.toDouble()
            }
            lecturerSalariesProperty.set(total)
            lecturerModel.totalLecturerSalaryExpenses.value = total

        } catch (e:Exception) {
            lecturerSalariesProperty.set(0.0)
        }
    }


    private fun diplomaTotalFees() {
        var total = 0.0
        try {
            diplomaController.items.forEach {
                total += it.diplomaStudentFees.value.toDouble()
            }
            diplomaFeesProperty.set(total)
            diplomaModel.totalFeesDiplomaExpenses.value = total

        } catch (e:Exception) {
            diplomaFeesProperty.set(0.0)
        }
    }

    private fun degreeTotalFees() {
        var total = 0.0
        try {
            degreeController.items.forEach {
                total += it.degreeStudentFees.value.toDouble()
            }
            degreeFeesProperty.set(total)
            degreeModel.totalFeesExpenses.value = total

        } catch (e:Exception) {
            degreeFeesProperty.set(0.0)
        }
    }

    private fun adminTotalSalaries() {
        var total = 0.0
        try {
            adminController.items.forEach {
                total += it.adminSalary.value.toDouble()
            }
            adminSalariesProperty.set(total)
            adminModel.totalAdminSalaryExpenses.value = total

        } catch (e:Exception) {
            adminSalariesProperty.set(0.0)
        }
    }

}

