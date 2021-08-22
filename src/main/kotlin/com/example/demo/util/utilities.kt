package com.example.demo.util

import org.joda.time.DateTime
import org.joda.time.LocalDate

fun DateTime.toJavaLocalDate(): LocalDate {
    return LocalDate(this.year)
}

fun LocalDate.toDate(default: DateTime = DateTime(1900)): DateTime {
    return DateTime(this.year)
}