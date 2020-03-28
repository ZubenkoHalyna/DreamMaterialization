package net.ukr.zubenko.g.dreammaterialization.holders

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.TaskLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task

class TaskHolder(inflater: LayoutInflater,
                 parent: ViewGroup,
                 openItemInfoActivity: (Task) -> Unit,
                 type: Int = R.layout.list_item_task) :
    BaseHolder<Task>(inflater, parent, type, openItemInfoActivity) {

    private val mTitleTextView = itemView.findViewById<TextView>(R.id.taskTitle)
    private val mDeadlineTextView = itemView.findViewById<TextView>(R.id.taskDeadlineDate)
    private lateinit var mTask: Task

    companion object {
        private const val TAG = "TaskHolder_class_log"
    }

    override val item: Task
        get() = mTask

    override fun bind(item: Task) {
        mTask = item
        mTitleTextView.text = mTask.mTitle
        mDeadlineTextView.text = mTask.strDateTime
    }

    override fun delete() {
        TaskLab.remove(mTask)
    }

    override fun bindItem(item: DbItem){
        if (item is Task)
            bind(item)
        else
            Log.i(TAG, "Try to bind ${item.javaClass} with a help of TaskHolder")
    }
}