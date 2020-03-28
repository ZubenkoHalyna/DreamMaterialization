package net.ukr.zubenko.g.dreammaterialization.data.database.tables.data

import android.text.format.DateFormat
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.TaskLab
import java.util.*

class Task(mId: UUID = UUID.randomUUID(),
           val mDreamId: UUID,
           val mTitle: String = "",
           val mDescription: String = "",
           val mDeadline: Date = Date()): DbItem(mId) {
    val strDateTime: String
        get() = DateFormat.format("dd.MM.yyyy  HH:mm", mDeadline).toString()
    val strDate: String
        get() = DateFormat.format("dd.MM.yyyy", mDeadline).toString()
    val strTime: String
        get() = DateFormat.format("HH:mm", mDeadline).toString()

    fun copy(
        id: UUID = mId,
        dreamId: UUID = mDreamId,
        title: String = mTitle,
        description: String = mDescription,
        deadline: Date = mDeadline): Task
    {
        val task = Task(id, dreamId, title, description, deadline)
        TaskLab.update(task)

        return task
    }
}