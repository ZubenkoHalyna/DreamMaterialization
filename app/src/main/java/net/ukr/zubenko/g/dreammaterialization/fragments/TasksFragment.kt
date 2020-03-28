package net.ukr.zubenko.g.dreammaterialization.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.activities.HabitInfoActivity
import net.ukr.zubenko.g.dreammaterialization.activities.TaskInfoActivity
import net.ukr.zubenko.g.dreammaterialization.adapters.TaskHabitAdapter
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.HabitLab
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.TaskLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task
import net.ukr.zubenko.g.dreammaterialization.holders.DbItemHolder

class TasksFragment: Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TaskHabitAdapter
    private lateinit var mNoTasksText: TextView
    private lateinit var mAddTaskButton: FloatingActionButton
    private lateinit var mAddHabitButton: FloatingActionButton
    private lateinit var mDream: Dream

    companion object {
        fun getInstance(dream: Dream): Fragment {
            val fr = TasksFragment()
            fr.mDream = dream
            return fr
        }
    }

    private fun taskSelected(item: DbItem) {
        val intent = TaskInfoActivity.newIntent(requireContext(), item as Task)
        startActivity(intent)
    }

    private fun habitSelected(item: DbItem) {
        val intent = HabitInfoActivity.newIntent(requireContext(), item as Habit)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tasks_fragment, container, false)
        mRecyclerView = view.findViewById(R.id.tasks_recycler_view) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

        if (! ::mDream.isInitialized)
            Dream.getFromInstanceState(savedInstanceState)?.let { dream ->
                mDream = dream
            }

        val simpleItemTouchCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                (viewHolder as? DbItemHolder)?.delete()
                updateUI()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

        mAddTaskButton =  view.findViewById(R.id.fabAddTask)
        mAddTaskButton.setOnClickListener {
            val t = Task(mDreamId = mDream.mId)
            TaskLab.add(t)
            taskSelected(t)
        }

        mAddHabitButton =  view.findViewById(R.id.fabAddHabit)
        mAddHabitButton.setOnClickListener {
            val h = Habit(mDreamId = mDream.mId)
            HabitLab.add(h)
            habitSelected(h)
        }

        mNoTasksText = view.findViewById(R.id.no_tasks_text)

        updateUI()

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Dream.saveToInstanceState(outState, mDream)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        if (this::mAdapter.isInitialized) {
            mAdapter.mItems.clear()
            mAdapter.mItems.addAll(getItems())
            mRecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }
        else
        {
            mAdapter = TaskHabitAdapter(getItems(), requireActivity(), ::taskSelected, ::habitSelected)
            mRecyclerView.adapter = mAdapter
        }

        mNoTasksText.visibility = if (TaskLab.mItems.size + HabitLab.mItems.size == 0)
            View.VISIBLE else View.INVISIBLE
    }

    private fun getItems(): MutableList<DbItem> {
        val items = mutableListOf<DbItem>()
        items.addAll(TaskLab.getTasksByDream(mDream))
        items.addAll(HabitLab.getHabitsByDream(mDream))
        return items
    }
}