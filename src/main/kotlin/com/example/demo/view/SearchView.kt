package com.example.demo.view

import com.example.demo.util.Searchable
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.awt.TextField

class SearchView : View() {
    override val root = textfield {
        promptText = "search"
        enableWhen(workspace.dockedComponentProperty.booleanBinding { it is Searchable })
        action {
            (workspace.dockedComponent as Searchable).onSearch(text)
        }

    }
}