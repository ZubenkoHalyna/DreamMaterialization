package net.ukr.zubenko.g.dreammaterialization.data.database.tables.data

import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import java.util.*

class Dream(mId: UUID = UUID.randomUUID(),
            val mTitle: String = "",
            val mDescription: String = ""): DbItem(mId) {

    val picFileName: String
        get() = "IMG_$mId.jpg"

    fun copy(
        id: UUID = mId,
        title: String = mTitle,
        description: String = mDescription): Dream
    {
        val dream = Dream(id, title, description)
        DreamLab.update(dream)

        return dream
    }
}
