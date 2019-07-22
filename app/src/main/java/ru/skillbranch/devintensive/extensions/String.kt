package ru.skillbranch.devintensive.extensions

import java.lang.Exception

private class EscData(
    val symbol:Char,
    val code:Int,
    val mnemocode:String,
    val hexCode:String = Integer.toHexString(code)){

    companion object Factory{
        private val dictEsc = listOf(
            EscData('"',34, "quot"),
            EscData('&' ,39, "amp"),
            EscData('<',60, "lt"),
            EscData('>',62, "gt"),
            EscData('\'',8216, "lsquo"),
            EscData('\'',8217, "rsquo")
        )
        fun parse(str:String):EscData?
        {
            return dictEsc.firstOrNull { data -> (str == data.mnemocode || str == "#x${data.hexCode}" || str == "#x${data.hexCode.toUpperCase()}" || str == "#${data.code}") }
        }
    }
}

fun String.truncate(length: Int = 16) : String {
    val ending = "..."

    var modifiedString = this.trim()

    if (modifiedString.length > length) {
        modifiedString = modifiedString.substring(0 until length)
        modifiedString = modifiedString.trimEnd()
        modifiedString += ending
    }

    return modifiedString
}

fun String.stripHtml() : String {
    return this
        .replace(Regex("<.*?>"), "")
        .replace(Regex(
            "(&amp;|&nbsp;|&quot;|&apos;|&lt;|&gt;|&#34;|&#39;|&#38;|&#60;|&#62;)"
        ), "")
        .replace(Regex(" {2,}"), " ")
}