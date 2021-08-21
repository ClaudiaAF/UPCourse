package com.example.demo.view

import Styles.Companion.topbar
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
                action {
                    find(MainView::class).replaceWith(StaffTable::class, sizeToScene = true, centerOnScreen = true)
                }
            }
            button("Courses"){
                isDefaultButton = true
                addClass(Styles.navButton)
                action {
                    find(MainView::class).replaceWith(CoursesPage::class, sizeToScene = true, centerOnScreen = true)
                }
            }
            button("Staff"){
                isDefaultButton = true
                addClass(Styles.navButton)
                action {
                    find(MainView::class).replaceWith(StaffPage::class, sizeToScene = true, centerOnScreen = true)
                }
            }


            spacer()
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