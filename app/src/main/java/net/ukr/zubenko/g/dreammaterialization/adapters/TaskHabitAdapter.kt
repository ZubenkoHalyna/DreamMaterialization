package net.ukr.zubenko.g.dreammaterialization.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task
import net.ukr.zubenko.g.dreammaterialization.holders.DbItemHolder
import net.ukr.zubenko.g.dreammaterialization.holders.HabitHolder
import net.ukr.zubenko.g.dreammaterialization.holders.TaskHolder

class TaskHabitAdapter(var mItems: MutableList<DbItem>,
                       private val activity: Activity,
                       private val openTaskInfoActivity: (Task) -> Unit,
                       private val openHabitInfoActivity: (Habit) -> Unit) :
    RecyclerView.Adapter<DbItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbItemHolder {
      val layoutInflater = LayoutInflater.from(activity)
      return if (viewType == 0)
          TaskHolder(layoutInflater, parent, openTaskInfoActivity)
      else HabitHolder(layoutInflater, parent, openHabitInfoActivity)
  }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position] is Task) 0 else 1
    }

    override fun onBindViewHolder(holder: DbItemHolder, position: Int) {
        val item = mItems[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}