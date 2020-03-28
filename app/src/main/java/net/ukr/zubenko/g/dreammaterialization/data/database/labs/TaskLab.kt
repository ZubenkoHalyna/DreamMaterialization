package net.ukr.zubenko.g.dreammaterialization.data.database.labs

import android.content.ContentValues
import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.TaskTable
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.TaskCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task

object TaskLab: Lab<Task>() {
    override val tableName: String
        get() = TaskTable.NAME

    fun getTasksByDream(dream: Dream): List<Task> {
        val cursor = queryItems("${TaskTable.Cols.DREAM_UUID} = ?", arrayOf(dream.mId.toString()))
        return selectByQuery(cursor)
    }

    override fun getContentValues(item: Task): ContentValues {
        val values = ContentValues()
        values.put(TaskTable.Cols.UUID, item.mId.toString())
        values.put(TaskTable.Cols.DREAM_UUID, item.mDreamId.toString())
        values.put(TaskTable.Cols.TITLE, item.mTitle)
        values.put(TaskTable.Cols.DESCRIPTION, item.mDescription)
        values.put(TaskTable.Cols.DEADLINE, item.mDeadline.time)
        return values
    }

    override fun getCursorWrapper(c: Cursor): BaseCursorWrapper<Task> {
        return TaskCursorWrapper(c)
    }
}