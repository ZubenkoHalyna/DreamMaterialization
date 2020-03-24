package net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper

import android.database.Cursor
import database.DbSchema.DreamViewTable
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView
import java.util.*


class DreamViewCursorWrapper(cursor: Cursor) : BaseCursorWrapper<DreamView>(cursor)
{
    override fun getItem(): DreamView? {
        val uuidString = getString(getColumnIndex(DreamViewTable.Cols.UUID))
        val left = getInt(getColumnIndex(DreamViewTable.Cols.LEFT))
        val top = getInt(getColumnIndex(DreamViewTable.Cols.TOP))
        val width = getInt(getColumnIndex(DreamViewTable.Cols.WIDTH))
        val height = getInt(getColumnIndex(DreamViewTable.Cols.HEIGHT))
        val angle = getInt(getColumnIndex(DreamViewTable.Cols.ANGLE))

        return DreamView(
            UUID.fromString(uuidString),
            left,
            top,
            width,
            height,
            angle
        )
    }
}