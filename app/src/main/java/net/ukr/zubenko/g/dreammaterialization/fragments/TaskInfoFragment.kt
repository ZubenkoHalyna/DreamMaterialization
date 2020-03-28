package net.ukr.zubenko.g.dreammaterialization.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Task
import java.util.*

class TaskInfoFragment : Fragment() {
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mDeadLineDate: Button
    private lateinit var mDeadLineTime: Button
    private lateinit var mTask: Task

    companion object {
        const val DIALOG_DATE = "DialogDate"
        const val DIALOG_TIME = "DialogTime"
        const val REQUEST_DATE = 0
        const val REQUEST_TIME = 1

        fun getInstance(task: Task): Fragment {
            val fr = TaskInfoFragment()
            fr.mTask = task
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.task_info_fragment, container, false)

        mTitle = view.findViewById(R.id.taskTitle)
        mTitle.setText(mTask.mTitle)
        mTitle.addTextChangedListener(getTextChangedListener { s ->
            mTask = mTask.copy(title = s)
        })

        mDescription = view.findViewById(R.id.taskDescription)
        mDescription.setText(mTask.mDescription)
        mDescription.addTextChangedListener(getTextChangedListener { s ->
            mTask = mTask.copy(description = s)
        })

        mDeadLineDate = view.findViewById(R.id.taskDeadlineDate)
        mDeadLineDate.text = mTask.strDate
        mDeadLineDate.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(mTask.mDeadline)
            dialog.setTargetFragment(this, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }

        mDeadLineTime = view.findViewById(R.id.taskDeadlineTime)
        mDeadLineTime.text = mTask.strTime
        mDeadLineTime.setOnClickListener {
            val dialog = TimePickerFragment.newInstance(mTask.mDeadline)
            dialog.setTargetFragment(this, REQUEST_TIME)
            dialog.show(fragmentManager, DIALOG_TIME)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            REQUEST_DATE -> {
                (data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as? Date)?.let {
                    mTask = mTask.copy(deadline = combineDateTime(it, mTask.mDeadline))
                }
                mDeadLineDate.text = mTask.strDate
            }

            REQUEST_TIME -> {
                (data?.getSerializableExtra(TimePickerFragment.EXTRA_HOUR) as? Int)?.let { hour ->
                    (data.getSerializableExtra(TimePickerFragment.EXTRA_MINUTE) as? Int)?.let { minute ->
                        mTask = mTask.copy(deadline = combineDateTime(mTask.mDeadline, hour, minute))
                    }
                }
                mDeadLineTime.text = mTask.strTime
            }
        }
    }


    private fun combineDateTime(date: Date, time: Date): Date {
        val timeCalendar = Calendar.getInstance()
        timeCalendar.time = time

        return combineDateTime(
            date,
            timeCalendar.get(Calendar.HOUR_OF_DAY),
            timeCalendar.get(Calendar.MINUTE)
        )
    }


    private fun combineDateTime(date: Date, hour: Int, minute: Int): Date {
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = date

        return GregorianCalendar(
            dateCalendar.get(Calendar.YEAR),
            dateCalendar.get(Calendar.MONTH),
            dateCalendar.get(Calendar.DAY_OF_MONTH),
            hour,
            minute
        ).time
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