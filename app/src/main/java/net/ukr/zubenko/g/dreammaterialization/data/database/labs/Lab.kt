package net.ukr.zubenko.g.dreammaterialization.data.database.labs

import android.database.sqlite.SQLiteDatabase
import java.util.*
import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import database.DbSchema
import net.ukr.zubenko.g.dreammaterialization.data.database.DbBaseHelper
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem

abstract class Lab<T: DbItem> {
    protected abstract val tableName: String
    protected abstract fun getContentValues(item: T): ContentValues
    protected abstract fun getCursorWrapper(c: Cursor): BaseCursorWrapper<T>
    private val mDatabase: SQLiteDatabase
        get() = DbBaseHelper.mDatabase

    val mItems: List<T>
    get() {
        val items = mutableListOf<T>()
        val cursor = queryItems(null, null)
        cursor.use {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                cursor.getItem()?.let {
                    items.add(it)
                }
                cursor.moveToNext()
            }
        }
        return items
    }

    fun getItem(id: UUID): T?
    {
        val cursor = queryItems(
            DbSchema.UUID_COL + " = ?",
            arrayOf(id.toString())
        )
        cursor.use {
            if (cursor.count == 0) {
                return null
            }
            cursor.moveToFirst()
            return cursor.getItem()
        }
    }

    fun indexOf(id: UUID) = mItems.indexOfFirst { it.mId == id }

    fun add(item: T) {
        val res: Long = mDatabase.insert(tableName, null, getContentValues(item))
        Log.i("add_dream_view", res.toString())
    }

    fun remove(item: T) {
        val uuidString = item.mId.toString()
        mDatabase.delete(tableName, DbSchema.UUID_COL + " = ?", arrayOf(uuidString))
    }

    fun update(item: T) {
        val uuidString = item.mId.toString()
        val values = getContentValues(item)
        mDatabase.update(
            tableName, values, DbSchema.UUID_COL + " = ?", arrayOf(uuidString)
        )
    }

    private fun queryItems(whereClause: String?, whereArgs: Array<String>?): BaseCursorWrapper<T> {
        return getCursorWrapper(mDatabase.query(tableName, null, whereClause, whereArgs, null, null, null))
    }

}