package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {
    val fullName = "$firstName $lastName"

    val nickName = Utils.transliteration(fullName)
    val initials = Utils.toInitials(firstName, lastName)
    val status = if (lastVisit == null) {
        "Ещё ни разу не был"
    } else if (isOnline) {
        "online"
    } else {
        "Последний раз был ${lastVisit.humanizeDiff()}"
    }

    return UserView(
        id,
        fullName = fullName,
        initials = initials,
        nickName = nickName,
        avatar = avatar,
        status = status
    )
}
