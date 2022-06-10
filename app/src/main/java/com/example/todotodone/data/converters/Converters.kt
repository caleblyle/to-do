package com.example.todotodone.data.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun convertLongToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun convertDateToLong(date: Date?): Long? {
        return date?.time
    }
}