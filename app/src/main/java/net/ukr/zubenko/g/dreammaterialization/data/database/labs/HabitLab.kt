package net.ukr.zubenko.g.dreammaterialization.data.database.labs

import android.content.ContentValues
import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.HabitTable
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.HabitCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit

object HabitLab: Lab<Habit>() {
    override val tableName: String
        get() = HabitTable.NAME

    fun getHabitsByDream(dream: Dream): List<Habit> {
        val cursor = queryItems("${HabitTable.Cols.DREAM_UUID} = ?", arrayOf(dream.mId.toString()))
        return selectByQuery(cursor)
    }

    override fun getContentValues(item: Habit): ContentValues {
        val values = ContentValues()
        values.put(HabitTable.Cols.UUID, item.mId.toString())
        values.put(HabitTable.Cols.DREAM_UUID, item.mDreamId.toString())
        values.put(HabitTable.Cols.TITLE, item.mTitle)
        values.put(HabitTable.Cols.DESCRIPTION, item.mDescription)
        values.put(HabitTable.Cols.PERIOD, item.mPeriod)
        return values

    }

    override fun getCursorWrapper(c: Cursor): BaseCursorWrapper<Habit> {
        return HabitCursorWrapper(c)
    }
}