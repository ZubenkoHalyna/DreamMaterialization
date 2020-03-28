package database

import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.DreamTable
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import java.util.*

class DreamCursorWrapper(cursor: Cursor) : BaseCursorWrapper<Dream>(cursor)
{
    override fun getItem(): Dream? {
        val uuidString = getString(getColumnIndex(DreamTable.Cols.UUID))
        val title = getString(getColumnIndex(DreamTable.Cols.TITLE))
        val description = getString(getColumnIndex(DreamTable.Cols.DESCRIPTION))

        return Dream(
            UUID.fromString(uuidString),
            title,
            description
        )
    }
}