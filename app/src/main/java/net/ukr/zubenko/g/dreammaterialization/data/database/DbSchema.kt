package net.ukr.zubenko.g.dreammaterialization.data.database

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

    object TaskTable {
        const val NAME = "tasks"

        object Cols {
            const val UUID = UUID_COL
            const val DREAM_UUID = "dream_uuid"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DEADLINE = "deadline"
        }
    }

    object HabitTable {
        const val NAME = "habits"

        object Cols {
            const val UUID = UUID_COL
            const val DREAM_UUID = "dream_uuid"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val PERIOD = "period"
        }
    }
}