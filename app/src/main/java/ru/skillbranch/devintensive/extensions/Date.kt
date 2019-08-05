package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY;

    fun plural(time: Int): String {
        val timeWord: String
        when {
            this == SECOND -> timeWord = when {
                time % 100 in 11..19 -> "секунд"
                time % 10 in 2..4 -> "секунды"
                time % 10 == 1 -> "секунду"
                else -> "секунд"
            }
            this == MINUTE -> timeWord = when {
                time % 100 in 11..19 -> "минут"
                time % 10 in 2..4 -> "минуты"
                time % 10 == 1 -> "минуту"
                else -> "минут"
            }
            this == HOUR -> timeWord = when {
                time % 100 in 11..19 -> "часов"
                time % 10 in 2..4 -> "часа"
                time % 10 == 1 -> "час"
                else -> "часов"
            }
            else -> timeWord = when {
                time % 100 in 11..19 -> "дней"
                time % 10 in 2..4 -> "дня"
                time % 10 == 1 -> "день"
                else -> "дней"
            }
        }
        return "$time $timeWord"
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, timeUnits: TimeUnits): Date {
    var time = this.time
    time += when (timeUnits) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val delta = date.time - this.time
    val absDelta = Math.abs(delta)

    val absMin = absDelta / MINUTE
    val absHour = absDelta / HOUR
    val absDay = absDelta / DAY

    return when (absDelta) {
        in 0..SECOND -> "только что"
        in SECOND..SECOND * 45 -> if (delta > 0) "несколько секунд назад" else "через несколько секунд"
        in SECOND * 45..SECOND * 75 -> if (delta > 0) "минуту назад" else "через минуту"
        in SECOND * 75..MINUTE * 45 -> if (delta > 0) "$absMin ${when {
            absMin % 100 in 11..19 -> "минут"
            absMin % 10 in 2..4 -> "минуты"
            absMin % 10 == 1L -> "минуту"
            else -> "минут"
        }} назад" else "через $absMin ${when {
            absMin % 100 in 11..19 -> "минут"
            absMin % 10 in 2..4 -> "минуты"
            absMin % 10 == 1L -> "минуту"
            else -> "минут"
        }}"
        in MINUTE * 45..MINUTE * 75 -> if (delta > 0) "час назад" else "через час"
        in MINUTE * 75..HOUR * 22 -> if (delta > 0) "$absHour ${when {
            absHour % 100 in 11..19 -> "часов"
            absHour % 10 in 2..4 -> "часа"
            absHour % 10 == 1L -> "час"
            else -> "часов"
        }
        } назад" else "через $absHour ${when {
            absHour % 100 in 11..19 -> "часов"
            absHour % 10 in 2..4 -> "часа"
            absHour % 10 == 1L -> "час"
            else -> "часов"
        }
        }"
        in HOUR * 22..HOUR * 26 -> if (delta > 0) "день назад" else "через день"
        in HOUR * 26..DAY * 360 -> if (delta > 0) "$absDay ${when {
            absDay % 100 in 11..19 -> "дней"
            absDay % 10 in 2..4 -> "дня"
            absDay % 10 == 1L -> "день"
            else -> "дней"
        }
        } назад" else "через $absDay ${when {
            absDay % 100 in 11..19 -> "дней"
            absDay % 10 in 2..4 -> "дня"
            absDay % 10 == 1L -> "день"
            else -> "дней"
        }
        }"
        in DAY * 360..Long.MAX_VALUE -> if (delta > 0) "более года назад" else "более чем через год"
        else -> throw IllegalStateException()
    }
}