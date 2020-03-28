package net.ukr.zubenko.g.dreammaterialization.activities

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.HabitLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit
import net.ukr.zubenko.g.dreammaterialization.fragments.HabitInfoFragment
import java.util.*

class HabitInfoActivity : SingleFragmentActivity() {
    companion object {
        const val EXTRA_HABIT_ID = "android.dream.materialization.habit_id"

        fun newIntent(context: Context, habit: Habit): Intent {
            val i = Intent(context, HabitInfoActivity::class.java)
            i.putExtra(EXTRA_HABIT_ID, habit.mId)
            return i
        }
    }

    override fun createFragment(): Fragment {
        val habitId = intent.getSerializableExtra(EXTRA_HABIT_ID) as UUID
        val habit = HabitLab.getItem(habitId)

        habit?.let {
            return HabitInfoFragment.getInstance(habit)
        }
        throw NullPointerException()
    }
}
