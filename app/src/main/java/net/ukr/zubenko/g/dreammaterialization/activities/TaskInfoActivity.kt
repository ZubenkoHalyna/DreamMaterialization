package net.ukr.zubenko.g.dreammaterialization.activities

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.TaskLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task
import net.ukr.zubenko.g.dreammaterialization.fragments.TaskInfoFragment
import java.lang.NullPointerException
import java.util.*

class TaskInfoActivity : SingleFragmentActivity() {
    companion object {
        const val EXTRA_TASK_ID = "android.dream.materialization.task_id"

        fun newIntent(context: Context, task: Task): Intent {
            val i = Intent(context, TaskInfoActivity::class.java)
            i.putExtra(EXTRA_TASK_ID, task.mId)
            return i
        }
    }

    override fun createFragment(): Fragment {
        val taskId = intent.getSerializableExtra(EXTRA_TASK_ID) as UUID
        val task = TaskLab.getItem(taskId)

        task?.let {
            return TaskInfoFragment.getInstance(task)
        }
        throw NullPointerException()
    }

}
