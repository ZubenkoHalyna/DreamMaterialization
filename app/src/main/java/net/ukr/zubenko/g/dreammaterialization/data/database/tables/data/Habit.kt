package net.ukr.zubenko.g.dreammaterialization.data.database.tables.data

import net.ukr.zubenko.g.dreammaterialization.data.database.labs.HabitLab
import java.util.*

class Habit(mId: UUID = UUID.randomUUID(),
            val mDreamId: UUID,
            val mTitle: String = "",
            val mDescription: String = "",
            val mPeriod: Int = 2): DbItem(mId) {

    fun copy(
        id: UUID = mId,
        dreamId: UUID = mDreamId,
        title: String = mTitle,
        description: String = mDescription,
        period: Int = mPeriod): Habit
    {
        val habit = Habit(id, dreamId, title, description, period)
        HabitLab.update(habit)

        return habit
    }
}