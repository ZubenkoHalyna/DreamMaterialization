package net.ukr.zubenko.g.dreammaterialization.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.Companion.DATABASE_NAME
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.Companion.VERSION
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.DreamTable
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.DreamViewTable
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.TaskTable
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.HabitTable

class DbBaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        lateinit var mDatabase: SQLiteDatabase
        fun init(context: Context) {
            mDatabase = DbBaseHelper(context).writableDatabase
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table ${DreamTable.NAME} (_id integer primary key autoincrement, " +
                "${DreamTable.Cols.UUID}, ${DreamTable.Cols.TITLE}, ${DreamTable.Cols.DESCRIPTION})")

        db.execSQL("create table ${DreamViewTable.NAME} (_id integer primary key autoincrement, " +
                "${DreamViewTable.Cols.UUID}, ${DreamViewTable.Cols.LEFT}, " +
                "${DreamViewTable.Cols.TOP}, ${DreamViewTable.Cols.WIDTH}, " +
                "${DreamViewTable.Cols.HEIGHT}, ${DreamViewTable.Cols.ANGLE})")

        db.execSQL("create table ${TaskTable.NAME} (_id integer primary key autoincrement, " +
                "${TaskTable.Cols.UUID}, ${TaskTable.Cols.DREAM_UUID}, " +
                "${TaskTable.Cols.TITLE}, ${TaskTable.Cols.DESCRIPTION}, " +
                "${TaskTable.Cols.DEADLINE})")

        db.execSQL("create table ${HabitTable.NAME} (_id integer primary key autoincrement, " +
                "${HabitTable.Cols.UUID}, ${HabitTable.Cols.DREAM_UUID}, " +
                "${HabitTable.Cols.TITLE}, ${HabitTable.Cols.DESCRIPTION}, " +
                "${HabitTable.Cols.PERIOD})")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}