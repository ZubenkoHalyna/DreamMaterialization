package net.ukr.zubenko.g.dreammaterialization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import java.util.*

class DreamInfoActivity : SingleFragmentActivity() {
    private lateinit var mDream: Dream

    override fun createFragment() = DreamInfoFragment.getInstance(mDream)

    companion object {
        const val EXTRA_DREAM_ID = "android.dream.materialization.dream_id"

        fun newIntent(packageContext: Context, dream: Dream): Intent {
            val intent = Intent(packageContext, DreamInfoActivity::class.java)
            intent.putExtra(EXTRA_DREAM_ID, dream.mId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val dreamId = intent.getSerializableExtra(EXTRA_DREAM_ID) as UUID
        DreamLab.getItem(dreamId)?.let {
            mDream = it
        }

        super.onCreate(savedInstanceState)
    }

}
