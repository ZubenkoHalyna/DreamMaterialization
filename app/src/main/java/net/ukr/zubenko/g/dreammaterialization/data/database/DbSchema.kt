package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import net.ukr.zubenko.g.dreammaterialization.data.database.DbBaseHelper

class DbSchema {
    companion object {
        const val UUID_COL = "uuid"
        const val VERSION = 1
        const val DATABASE_NAME = "dreamBase.db"
    }

    object DreamTable {
        const val NAME = "dreams"

        object Cols {
            const val UUID = UUID_COL
            const val TITLE = "title"
            const val DESCRIPTION = "description"
        }
    }

    object DreamViewTable {
        const val NAME = "dream_views"

        object Cols {
            const val UUID = UUID_COL
            const val LEFT = "left"
            const val TOP = "top"
            const val WIDTH = "width"
            const val HEIGHT = "height"
            const val ANGLE = "angle"
        }
    }
}