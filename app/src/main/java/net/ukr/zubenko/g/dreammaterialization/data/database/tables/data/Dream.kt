package net.ukr.zubenko.g.dreammaterialization.data.database.tables.data

import android.os.Bundle
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

    companion object {
        private const val DREAM_ID = "dream_id"
        fun getFromInstanceState(savedInstanceState: Bundle?): Dream? {
            savedInstanceState?.let {
                it.getString(DREAM_ID)?.let { id ->
                    DreamLab.getItem(UUID.fromString(id))?.let { dream ->
                        return dream
                    }
                }
            }
            return null
        }

        fun saveToInstanceState(outState: Bundle, dream: Dream) {
            outState.putString(DREAM_ID, dream.mId.toString())
        }
    }
}
