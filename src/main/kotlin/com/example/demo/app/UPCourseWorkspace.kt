package com.example.demo.app

import com.example.demo.controller.ItemController
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
import com.example.demo.view.DegreeStudentsView
import com.example.demo.view.DiplomaStudentsView
import com.example.demo.view.EditView
import org.joda.time.DateTime
import java.math.BigDecimal

class UPCourseWorkspace : Workspace("UPCourse", NavigationMode.Tabs) {
    init {
        //initialise db
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-UPCourse.db", driver = "org.sqlite.JDBC")
        createTables()




        //controllers
        ItemController()

        //dock views
        dock<EditView>()
        dock<DegreeStudentsView>()
        dock<DiplomaStudentsView>()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}

