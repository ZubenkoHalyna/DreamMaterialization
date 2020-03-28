package net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper

import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.HabitTable
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit
import java.util.*

class HabitCursorWrapper(cursor: Cursor) : BaseCursorWrapper<Habit>(cursor) {
    override fun getItem(): Habit? {
        val uuidString = getString(getColumnIndex(HabitTable.Cols.UUID))
        val dreamUuid = getString(getColumnIndex(HabitTable.Cols.DREAM_UUID))
        val title = getString(getColumnIndex(HabitTable.Cols.TITLE))
        val description = getString(getColumnIndex(HabitTable.Cols.DESCRIPTION))
        val period = getInt(getColumnIndex(HabitTable.Cols.PERIOD))

        return Habit(
            UUID.fromString(uuidString),
            UUID.fromString(dreamUuid),
            title,
            description,
            period
        )
    }
}
