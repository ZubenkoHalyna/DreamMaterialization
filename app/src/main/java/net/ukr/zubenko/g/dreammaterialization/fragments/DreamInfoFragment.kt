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
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream

class DreamInfoFragment: Fragment() {
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mDream: Dream

    companion object {
        fun getInstance(dream: Dream): Fragment {
            val fr = DreamInfoFragment()
            fr.mDream = dream
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dream_info_fragment, container, false)

        mTitle = view.findViewById(R.id.dreamTitle)
        mTitle.setText(mDream.mTitle)
        mTitle.addTextChangedListener(getTextChangedListener { s ->
            mDream = mDream.copy(title = s)
        })

        mDescription = view.findViewById(R.id.dreamDescription)
        mDescription.setText(mDream.mDescription)
        mDescription.addTextChangedListener(getTextChangedListener { s ->
            mDream = mDream.copy(description = s)
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