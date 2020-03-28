package net.ukr.zubenko.g.dreammaterialization.holders

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.HabitLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit

class HabitHolder(inflater: LayoutInflater,
                  parent: ViewGroup,
                  openItemInfoActivity: (Habit)->  Unit,
                  type: Int = R.layout.list_item_habit):
    BaseHolder<Habit>(inflater, parent, type, openItemInfoActivity) {

    private val mTitleTextView = itemView.findViewById<TextView>(R.id.habitTitle)
    private val mPeriodTextView = itemView.findViewById<TextView>(R.id.habitFrequency)
    private lateinit var mHabit: Habit

    companion object {
        private const val TAG = "HabitHolder_class_log"
    }

    override val item: Habit
        get() = mHabit

    override fun bind(item: Habit) {
        mHabit = item
        mTitleTextView.text = mHabit.mTitle
        mPeriodTextView.text = "${mHabit.mPeriod}"
    }

    override fun delete() {
        HabitLab.remove(mHabit)
    }

    override fun bindItem(item: DbItem){
        if (item is Habit)
            bind(item)
        else
            Log.i(TAG, "try to bind ${item.javaClass} with a help of HabitHolder")
    }
}