package net.ukr.zubenko.g.dreammaterialization.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Habit

class HabitInfoFragment : Fragment() {
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mPeriod: EditText
    private lateinit var mHabit: Habit

    companion object {
        fun getInstance(habit: Habit): Fragment {
            val fr = HabitInfoFragment()
            fr.mHabit = habit
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.habit_info_fragment, container, false)

        mTitle = view.findViewById(R.id.habitTitle)
        mTitle.setText(mHabit.mTitle)
        mTitle.addTextChangedListener(getTextChangedListener { s ->
            mHabit = mHabit.copy(title = s)
        })

        mDescription = view.findViewById(R.id.habitDescription)
        mDescription.setText(mHabit.mDescription)
        mDescription.addTextChangedListener(getTextChangedListener { s ->
            mHabit = mHabit.copy(description = s)
        })

        mPeriod = view.findViewById(R.id.habitFrequency)
        mPeriod.setText(mHabit.mPeriod.toString())
        mPeriod.addTextChangedListener(getTextChangedListener{ s ->
            mHabit = mHabit.copy(period = s.toInt())
        })

        return view
    }

    private fun getTextChangedListener(textChangeAction: (String) -> Unit) =
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textChangeAction(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        }
}