package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int) : String {
        val pluralForm = Utils.getPluralForm(value.toLong(), Utils.getPluralsForms(this))
        return "$value $pluralForm"
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time

    return this
}
fun Date.humanizeDiff(date: Date = Date()): String {
    val now = this.time
    val then = date.time

    val msDiff = now - then

    val days = msDiff / DAY
    val absDays = Math.abs(days)

    val hours = msDiff / HOUR
    val absHours = Math.abs(hours)

    val minutes = msDiff / MINUTE
    val absMinutes = Math.abs(minutes)

    val seconds = msDiff / SECOND
    val absSeconds = Math.abs(seconds)


    if (absDays > 360) {
        return if (days > 0) {
            "более чем через год" // более года
        } else {
            "более года назад"
        }
    }

    if (absHours > 26) {
        val pluralForm = Utils.getPluralForm(absDays, Utils.getPluralsForms(TimeUnits.DAY))

        return if (days > 0) {
            "через $absDays $pluralForm" // N дней назад
        } else {
            "$absDays $pluralForm назад"
        }
    }

    if (absHours in 23..26) {
        return if (hours > 0) {
            "через день"                // день назад
        } else {
            "день назад"
        }
    }


    if (absMinutes > 75) {
        val pluralForm = Utils.getPluralForm(absHours, Utils.getPluralsForms(TimeUnits.HOUR))

        return if (hours > 0) {
            "через $absHours $pluralForm" // N часов назад
        } else {
            "$absHours $pluralForm назад"
        }
    }


    if (absMinutes in 46..75) {
        return if (minutes > 0) {
            "через час"                 // Час назад
        } else {
            "час назад"
        }
    }

    if (absSeconds > 75) {
        val pluralForm = Utils.getPluralForm(absMinutes, Utils.getPluralsForms(TimeUnits.MINUTE))

        return if (minutes > 0) {
            "через $absMinutes $pluralForm"
        } else {
            "$absMinutes $pluralForm назад"
        }
    }

    if (absSeconds in 46..75) {
        if (seconds > 0) {
            return "через минуту"       // N минут назад
        } else {
            return "минуту назад"
        }
    }

    if (absSeconds in 2..45) {
        return if (seconds > 0) {
            "через несколько секунд"    //  несколко секунд назад
        } else {
            "несколько секунд назад"
        }
    }

    if (Math.abs(seconds) in 0..1) {    // только что
        return "только что"
    }

    return ""
}
