package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.app.Styles.Companion.borderLineColor
import com.example.demo.app.Styles.Companion.darkTextColor
import com.example.demo.app.Styles.Companion.defaultContentPadding
import com.example.demo.app.Styles.Companion.defaultSpacing
import com.example.demo.app.Styles.Companion.detail
import com.example.demo.app.Styles.Companion.h1
import com.example.demo.app.Styles.Companion.h2
import com.example.demo.app.Styles.Companion.regularTable
import com.example.demo.app.Styles.Companion.rowWrapper
import com.example.demo.app.Styles.Companion.stat
import com.example.demo.app.Styles.Companion.successButton
import com.example.demo.app.Styles.Companion.userinfo
import com.example.demo.app.Styles.Companion.userscreen
import com.example.demo.controller.PersonController
import com.example.demo.model.Repo
import com.example.demo.model.User
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.WizardStyles.Companion.bold
import java.awt.Button
import java.awt.Event.HOME
import java.awt.Label
import java.awt.TextArea
import javax.swing.GroupLayout

class MainView : View("User Screen") {
    val controller: PersonController by inject()

    override val root = borderpane {
        top = vbox {
            addClass(Styles.rowWrapper)
            add(TopBar::class)
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            spacing = 6.0
            label("Dashboard").addClass(h1)
        }

        center = hbox {
            addClass(Styles.rowWrapper)
            tableViews()
            chartView()
        }
    }

    fun HBox.tableViews() = tableview(controller.persons) {
        column("Student Name", Person::nameProperty)
        column("Enrolment", Person::nameProperty)
        column("Invoice nr.", Person::nameProperty)
        column("Status", Person::nameProperty)
        column("Amount", Person::nameProperty)
        smartResize()
    }

    fun HBox.chartView() = gridpane(){
        row {

        }
         row {
             linechart("linechart", CategoryAxis(), NumberAxis()) {
                 series("month") {
                     data("jan", 10)
                     data("feb", 20)
                     data("mar", 5)
                 }
                 series("week") {
                     data("jan", 1)
                     data("feb", 2)
                 }
             }
         }
    }
}
