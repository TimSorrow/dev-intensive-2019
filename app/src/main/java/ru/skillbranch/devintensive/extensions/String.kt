package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
    return if (this.trim().length <= value)
        this.trim()
    else
        this.trim().substring(0, value + 1).trim() + "..."
}

fun String.stripHtml(): String {
    var result = this.replace(Regex("""<[^>]*>"""), "")
    result = result.replace(Regex("""&(.*);"""), "")
    result = result.replace(Regex("""\s{2,}"""), " ").trim()
    return result
}