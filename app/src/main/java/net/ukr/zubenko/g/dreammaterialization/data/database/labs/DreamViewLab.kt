package net.ukr.zubenko.g.dreammaterialization.data.database.labs

import android.content.ContentValues
import android.database.Cursor
import net.ukr.zubenko.g.dreammaterialization.data.database.DbSchema.DreamViewTable
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.BaseCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.cursor.wrapper.DreamViewCursorWrapper
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView

object DreamViewLab : Lab<DreamView>() {
    override val tableName: String
        get() = DreamViewTable.NAME

    override fun getContentValues(item: DreamView): ContentValues {
        val values = ContentValues()
        values.put(DreamViewTable.Cols.UUID, item.mId.toString())
        values.put(DreamViewTable.Cols.LEFT, item.mLeft)
        values.put(DreamViewTable.Cols.TOP, item.mTop)
        values.put(DreamViewTable.Cols.WIDTH, item.mWidth)
        values.put(DreamViewTable.Cols.HEIGHT, item.mHeight)
        values.put(DreamViewTable.Cols.ANGLE, item.mRotationAngle)
        return values
    }

    override fun getCursorWrapper(c: Cursor): BaseCursorWrapper<DreamView> {
        return DreamViewCursorWrapper(c)
    }
}