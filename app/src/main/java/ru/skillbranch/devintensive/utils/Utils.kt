package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.TimeUnits

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName == null) {
            return null to null
        }

        val trimmedFullName = fullName.trim()

        if (trimmedFullName.isEmpty()) {
            return null to null
        }

        val parts: List<String>? = trimmedFullName.split(" ")

        val firstName: String? = parts?.getOrNull(0)
        val lastName: String? = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val dictionary = getTransliterationDictionary()

        return payload
            .replace(" ", divider)
            .toCharArray()
            .joinToString("") { symbol ->
                val lowerCasedSymbol = symbol.toLowerCase().toString()
                val mapping = dictionary[lowerCasedSymbol]

                if (mapping != null) {
                    if (symbol == symbol.toUpperCase()) {
                        mapping.capitalize()
                    } else {
                        mapping
                    }
                } else {
                    symbol.toString()
                }
            }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName == null && lastName == null) {
            return null
        }

        val notNullableValues = listOf(firstName, lastName).filter { it != null }
        val notEmptyValues = notNullableValues.filter { it!!.trim().isNotEmpty() }

        return if (notEmptyValues.isEmpty()) {
            null
        } else {
            notEmptyValues.map { it!![0].toUpperCase() }.joinToString("")
        }
    }

    private fun getTransliterationDictionary() : HashMap<String, String> = hashMapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )

    fun getPluralForm(value: Long, plurals: Array<String>) : String {
        val mod100 = value % 100

        // 1, 21, 31, ...
        val firstFormCases = LongRange(0, 9).map { it * 10 + 1 }.filter { it != 11L }

        // 2, 3, 4, 22, 23, 24, ...
        val secondFormCases = LongRange(0, 9).map {
            arrayOf(it * 10 + 2, it * 10 + 3, it * 10 + 4)
        }.toTypedArray().flatten().filter { it !in 12..14 }

        return when(mod100) {
            in firstFormCases -> plurals[0]
            in secondFormCases -> plurals[1]
            else -> plurals[2]
        }
    }

    fun getPluralsForms(units: TimeUnits) : Array<String> {
        return when(units) {
            TimeUnits.SECOND -> arrayOf("секунду", "секунды", "секунд")
            TimeUnits.MINUTE -> arrayOf("минуту", "минуты", "минут")
            TimeUnits.HOUR -> arrayOf("час", "часа", "часов")
            TimeUnits.DAY -> arrayOf("день", "дня", "дней")
        }
    }
}