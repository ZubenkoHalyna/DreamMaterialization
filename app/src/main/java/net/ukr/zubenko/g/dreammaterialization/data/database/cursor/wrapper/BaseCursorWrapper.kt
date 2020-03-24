package net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper

import android.database.Cursor
import android.database.CursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem

abstract class BaseCursorWrapper<T: DbItem>(cursor: Cursor): CursorWrapper(cursor) {
    abstract fun getItem(): T?
}