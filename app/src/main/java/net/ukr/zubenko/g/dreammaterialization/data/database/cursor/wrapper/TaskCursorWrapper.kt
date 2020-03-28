package net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper

import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.TaskTable
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task
import java.util.*

class TaskCursorWrapper(cursor: Cursor) : BaseCursorWrapper<Task>(cursor) {
    override fun getItem(): Task? {
        val uuidString = getString(getColumnIndex(TaskTable.Cols.UUID))
        val dreamUuid = getString(getColumnIndex(TaskTable.Cols.DREAM_UUID))
        val title = getString(getColumnIndex(TaskTable.Cols.TITLE))
        val description = getString(getColumnIndex(TaskTable.Cols.DESCRIPTION))
        val deadline = getLong(getColumnIndex(TaskTable.Cols.DEADLINE))

        return Task(
            UUID.fromString(uuidString),
            UUID.fromString(dreamUuid),
            title,
            description,
            Date(deadline)
        )
    }
}