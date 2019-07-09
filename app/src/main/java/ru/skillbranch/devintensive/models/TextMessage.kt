package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var text: String?
): BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String {
        val status = when (isIncoming) {
            true -> "получил"
            false -> "отправил"
        }

        return "${from?.firstName} $status сообщение \"$text\" ${date.humanizeDiff()}"
    }

}