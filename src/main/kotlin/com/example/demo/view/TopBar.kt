package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.app.Styles.Companion.topbar
import tornadofx.*

class TopBar : Fragment() {
    override val root = hbox {
        addClass(topbar)
         contentBox{
            label {
//                addClass(Styles.logoIcon, Styles.icon, Styles.medium)
            }
            label("UPCourse")
             button("Fees"){
                 isDefaultButton = true
                 addClass(Styles.navButton)
             }
             button("Courses"){
                 isDefaultButton = true
                 addClass(Styles.navButton)

             }
             button("Staff"){
                 isDefaultButton = true
                 addClass(Styles.navButton)
                 action {
                     find(MainView::class).replaceWith(StaffPage::class, sizeToScene = true, centerOnScreen = true)
                 }
             }
             button("Salaries"){
                 isDefaultButton = true
                 addClass(Styles.navButton)
             }
             button("Sign out") {
                 isDefaultButton = true
                 addClass(Styles.successButton)
                 action {
                     find(MainView::class).replaceWith(LoginScreen::class, sizeToScene = true, centerOnScreen = true)
                 }
             }

        }
    }
}
