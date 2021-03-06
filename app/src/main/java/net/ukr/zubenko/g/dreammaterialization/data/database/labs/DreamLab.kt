package net.ukr.zubenko.g.dreammaterialization.data.database.labs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import database.DreamCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.PictureUtils
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.DreamTable
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import java.io.File

object DreamLab : Lab<Dream>() {
    private const val TAG = "DreamLab_class_log"
    private lateinit var mContextFileDir: File
    private lateinit var database: SQLiteDatabase

    override val tableName: String
        get() = DreamTable.NAME

    fun init(context: Context) {
        mContextFileDir = context.filesDir
    }

    override fun getContentValues(item: Dream): ContentValues {
        val values = ContentValues()
        values.put(DreamTable.Cols.UUID, item.mId.toString())
        values.put(DreamTable.Cols.TITLE, item.mTitle)
        values.put(DreamTable.Cols.DESCRIPTION, item.mDescription)
        return values
    }

    override fun getCursorWrapper(c: Cursor): BaseCursorWrapper<Dream> {
        return DreamCursorWrapper(c)
    }

    fun deleteRecursively(dream: Dream) {
        PictureUtils.deletePicture(getPictureFile(dream))

        val dreamView = DreamViewLab.getItem(dream.mId)
        if (dreamView == null) {
            Log.w(TAG, "Deleting dream (${dream.mId}), dreamView not found")
        } else {
            DreamViewLab.remove(dreamView)
            Log.i(TAG, "DreamView deleted (${dream.mId})")
        }

        TaskLab.removeTasks(dream)
        Log.i(TAG, "Tasks for dream (${dream.mId}) deleted ")

        HabitLab.removeHabits(dream)
        Log.i(TAG, "Habits for (${dream.mId}) dream deleted ")

        remove(dream)
        Log.i(TAG, "Dream deleted (${dream.mId})")
    }

    fun getPictureFile(dream: Dream): File {
        return File(mContextFileDir, dream.picFileName)
    }

    fun getPictureFile(fileName: String): File {
        return File(mContextFileDir, fileName)
    }
}