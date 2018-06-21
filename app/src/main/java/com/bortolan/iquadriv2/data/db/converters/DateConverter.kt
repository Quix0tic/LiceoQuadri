package com.bortolan.iquadriv2.data.db.converters

import androidx.room.TypeConverter
import java.util.*

open class DateConverter {

    @TypeConverter
    fun convertDateToLong(date: Date) = date.time

    @TypeConverter
    fun convertLongtoDate(long: Long) = Date(long)
}