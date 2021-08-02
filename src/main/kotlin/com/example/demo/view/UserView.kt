package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.model.User
import javafx.geometry.Insets
import javafx.scene.control.Alert
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import tornadofx.*

class UserView : View("User Screen") {
    val model : User by inject()

    override val root = borderpane {
        addClass(Styles.userscreen)
        top = vbox {
            addClass(Styles.rowWrapper)
            add(TopBar::class)
        }

        center = hbox {
            addClass(Styles.rowWrapper)
            userInfo()
            userDetail()
        }
    }

    fun HBox.userDetail() = vbox {
        addClass(Styles.detail, Styles.defaultSpacing, Styles.defaultContentPadding)
        hbox {
            label("Info") {
                addClass(Styles.h2)
                graphic = label().addClass(Styles.repoIcon, Styles.icon)
            }
            spacer()
            button("Edit", javafx.scene.control.Label().addClass(Styles.commentIcon, Styles.icon)) {
                addClass(Styles.successButton)
                setOnAction {
                    alert(Alert.AlertType.WARNING, "Not implemented", "This functionality has not been added yet!")
                }
                action {
                    replaceWith<EditView>()
                }
            }
        }
        form {
            fieldset("Personal Information") {
                field("Name") {
                    textfield(model.name).required()
                }

                field("Birthday") {
                    datepicker(model.birthday)
                }
            }

            fieldset("Address") {
                field("Street") {
                    textfield(model.street).required()
                }
                field("Zip / City") {
                    textfield(model.zip) {

                        required()
                    }
                    textfield(model.city).required()
                }
            }

            button("Save") {
                action {

                }

                enableWhen(model.valid)
            }
        }

    }

    fun HBox.userInfo() = vbox {
        addClass(Styles.userinfo)
        addClass(Styles.defaultSpacing)
        imageview {
        }
        vbox {
            label("John").addClass(Styles.h1)
            label("Wayne").addClass(Styles.h2)
        }
        hyperlink("Add a bio") {
            padding = Insets(0.0)
        }
        hbox {
            style {
                borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Styles.borderLineColor, Color.TRANSPARENT)
            }
        }
        vbox {
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            spacing = 6.0

            label("Location") {
                textFill = Color.BLACK
                graphicTextGap = 10.0
                graphic = label().addClass(Styles.locationIcon, Styles.icon)
            }
            label("Blog") {
                textFill = Color.BLACK
                graphicTextGap = 10.0
                graphic = label().addClass(Styles.linkIcon, Styles.icon)
            }
            label("Joined") {
                textFill = Color.BLACK
                graphicTextGap = 10.0
                graphic = label().addClass(Styles.clockIcon, Styles.icon)
            }
        }
        hbox {
            addClass(Styles.stat)
            vbox {
                label("34444")
                text("Followers") {
                    fill = Styles.darkTextColor
                }
            }
            vbox {
                label("77")
                text("Following") {
                    fill = Styles.darkTextColor
                }
            }
        }
    }
}