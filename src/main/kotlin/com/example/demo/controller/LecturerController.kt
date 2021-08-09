package com.example.demo.controller

import com.example.demo.model.Lecturers
import com.example.demo.model.LecturersDetailModel
import javafx.beans.property.Property
import tornadofx.*

class LecturerController {
    val lecturers = SortedFilteredList<Lecturers>()
    val selectedLecturer = LecturersDetailModel()

    private fun getLecturerID(id: Int) :Int {
        var i = -1
        lecturers.forEachIndexed { index, lecturers ->
            if (lecturers.lecturerId == id) {
                i = index
            }
        }
        return i
    }
    fun deleteLecturerByID(id: Property<Number>) {
        lecturers.removeAt(getLecturerID(id.value.toInt()))
    }
}