package com.example.demo.app

import com.example.demo.controller.*
import com.example.demo.model.ExpensesEntryTbl
import com.example.demo.util.createTables
import com.example.demo.util.enableConsoleLogger
import com.example.demo.util.execute
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import tornadofx.*
import java.time.LocalDate
import com.example.demo.util.toDate
import com.example.demo.view.*
import javafx.stage.Stage
import org.joda.time.DateTime
import java.awt.Color
import java.math.BigDecimal

class UPCourseWorkspace : Workspace("UPCourse", NavigationMode.Tabs) {
    init {
        //initialise db
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-UPCourse.db", driver = "org.sqlite.JDBC")
        createTables()

        //controllers
        ItemController()
        AdminController()
        DegreeStudentController()
        DiplomaStudentController()
        LecturerController()
        SubjectsController()

        //dock views
        dock<MainView>()
        dock<DegreeStudentsView>()
        dock<DiplomaStudentsView>()
        dock<AdminView>()
        dock<LecturerView>()
        dock<SubjectsView>()
        dock<FundsPool>()



        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }

}

