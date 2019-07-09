package ru.skillbranch.devintensive.extensions

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