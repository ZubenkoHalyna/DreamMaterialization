package net.ukr.zubenko.g.dreammaterialization.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream

class TasksFragment: Fragment() {
    private lateinit var mDream: Dream

    companion object {
        fun getInstance(dream: Dream): Fragment {
            val fr = TasksFragment()
            fr.mDream = dream
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tasks_fragment, container, false)

        return view
    }
}