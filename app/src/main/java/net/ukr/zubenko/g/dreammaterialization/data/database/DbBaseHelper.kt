package net.ukr.zubenko.g.dreammaterialization.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import database.DbSchema
import database.DbSchema.Companion.DATABASE_NAME
import database.DbSchema.Companion.VERSION
import database.DbSchema.DreamTable

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

        db.execSQL("create table ${DbSchema.DreamViewTable.NAME} (_id integer primary key autoincrement, " +
                "${DbSchema.DreamViewTable.Cols.UUID}, ${DbSchema.DreamViewTable.Cols.LEFT}, " +
                "${DbSchema.DreamViewTable.Cols.TOP}, ${DbSchema.DreamViewTable.Cols.WIDTH}, " +
                "${DbSchema.DreamViewTable.Cols.HEIGHT}, ${DbSchema.DreamViewTable.Cols.ANGLE})")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}