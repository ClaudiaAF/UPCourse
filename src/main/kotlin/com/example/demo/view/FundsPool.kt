package com.example.demo.view

import com.example.demo.controller.*
import com.example.demo.model.*
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import tornadofx.*
import java.lang.Exception
import javax.xml.soap.Text

class FundsPool : View ("Funds Pool") {
    var totalFundsPoolLabel: Label by singleAssign()
    val totalFundsProperty = SimpleDoubleProperty(0.0)

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

    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        lecturerTotalSalaries()
        subjectsTotalPrice()
        diplomaTotalFees()
        degreeTotalFees()
        adminTotalSalaries()


//        pieItemsData.add(PieChart.Data("Admin Expenses", lecturerSalariesProperty.value.toDouble(), "Admin"))

    }

    override val root = borderpane {
        top =vbox {
            piechart("Total Expenses") {
                data("Lecturers Expenses", lecturerSalariesProperty.value.toDouble())
                data("Admin Expenses", adminSalariesProperty.value.toDouble())

                paddingTop = 40
            }

            hbox (spacing = 30, alignment = Pos.CENTER){
                lecturerSalariesLabel = label {
                    if (lecturerSalariesProperty.doubleValue() != 0.0) {
                        bind(
                            Bindings.concat(
                                "Lecturers Salary Expenses: ",
                                "R",
                                Bindings.format("%.2f", lecturerSalariesProperty)
                            )
                        )
                    } else {

                    }
                    paddingTop = 20
                }

                adminSalariesLabel = label {
                    if (adminSalariesProperty.doubleValue() != 0.0) {
                        bind(
                            Bindings.concat(
                                "Admin Salary Expenses: ",
                                "R",
                                Bindings.format("%.2f", adminSalariesProperty)
                            )
                        )
                    } else {

                    }
                    paddingTop = 20
                }
            }

            vbox {
                piechart("Total Owed To UPCourse") {
                    data("Subjects Total Owed", subjectsPriceProperty.value.toDouble())
                    data("Diploma Fees Owed", diplomaFeesProperty.value.toDouble())
                    data("Degree Fees Owed", degreeFeesProperty.value.toDouble())

                    paddingTop = 40
                }

                hbox (spacing = 30, alignment = Pos.CENTER){
                    //subjects here
                    subjectsPriceLabel = label {
                        if (subjectsPriceProperty.doubleValue() != 0.0) {
                            bind(
                                Bindings.concat(
                                    "Subjects Total Owed: ",
                                    "R",
                                    Bindings.format("%.2f", subjectsPriceProperty)
                                )
                            )
                        } else {

                        }
                        paddingTop = 20
                    }

                    //diploma here
                    diplomaFeesLabel = label {
                        if (diplomaFeesProperty.doubleValue() != 0.0) {
                            bind(Bindings.concat("Diploma Fees Owed: ", "R", Bindings.format("%.2f", diplomaFeesProperty)))
                        } else {

                        }
                        paddingTop = 20
                    }

                    //degree here
                    degreeFeesLabel = label {
                        if (degreeFeesProperty.doubleValue() != 0.0) {
                            bind(Bindings.concat("Degree Fees Owed: ", "R", Bindings.format("%.2f", degreeFeesProperty)))
                        } else {

                        }
                        paddingTop = 20
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

