package ru.skillbranch.devintensive.utils

import android.content.Context
import java.lang.StringBuilder

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        if (fullName.isNullOrBlank()) return null to null

        val parts: List<String> = fullName.split(" ")
        val firstName = parts.getOrNull(0)
        val lastName = parts.getOrNull(1)

        return when {
            firstName.isNullOrBlank() -> null to lastName
            lastName.isNullOrBlank() -> firstName to null
            else -> firstName to lastName
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val dict = makeDict()
        dict[' '] = divider
        val sb = StringBuilder()
        for (index in payload.indices) {
            when {
                payload[index] in dict -> sb.append(dict[payload[index]])
                payload[index].isUpperCase() && (payload[index] < 'A' || payload[index] > 'Z') ->
                    sb.append(dict[payload[index].toLowerCase()]?.capitalize())
                else -> sb.append(payload[index])
            }
        }
        return sb.toString()
    }

    private fun makeDict(): HashMap<Char, String> {
        val dict = HashMap<Char, String>()
        dict['а'] = "a"
        dict['б'] = "b"
        dict['в'] = "v"
        dict['г'] = "g"
        dict['д'] = "d"
        dict['е'] = "e"
        dict['ё'] = "e"
        dict['ж'] = "zh"
        dict['з'] = "z"
        dict['и'] = "i"
        dict['й'] = "i"
        dict['к'] = "k"
        dict['л'] = "l"
        dict['м'] = "m"
        dict['н'] = "n"
        dict['о'] = "o"
        dict['п'] = "p"
        dict['р'] = "r"
        dict['с'] = "s"
        dict['т'] = "t"
        dict['у'] = "u"
        dict['ф'] = "f"
        dict['х'] = "h"
        dict['ц'] = "c"
        dict['ч'] = "ch"
        dict['ш'] = "sh"
        dict['щ'] = "sh'"
        dict['ъ'] = ""
        dict['ы'] = "i"
        dict['ь'] = ""
        dict['э'] = "e"
        dict['ю'] = "yu"
        dict['я'] = "ya"

        return dict
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        // both == null
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null

        // both are not null
        if (!firstName.isNullOrBlank() && !lastName.isNullOrBlank()) {
            return "${firstName.toUpperCase()[0]}${lastName.toUpperCase()[0]}"
        } else if (!firstName.isNullOrBlank())
            return "${firstName.toUpperCase()[0]}"
        return "${lastName?.toUpperCase()?.get(0)}"
    }

    fun convertPxToDp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun convertDpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun convertSpToPx(context: Context, sp: Int): Int {
        return sp * context.resources.displayMetrics.scaledDensity.toInt()
    }
}