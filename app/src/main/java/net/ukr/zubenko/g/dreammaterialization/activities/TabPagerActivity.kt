package net.ukr.zubenko.g.dreammaterialization.activities

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.os.Bundle
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import java.util.*
import android.support.v7.app.AppCompatActivity
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.adapters.TabAdapter
import net.ukr.zubenko.g.dreammaterialization.fragments.DreamInfoFragment
import net.ukr.zubenko.g.dreammaterialization.fragments.PictureFragment
import net.ukr.zubenko.g.dreammaterialization.fragments.TasksFragment

class TabPagerActivity : AppCompatActivity() {
    private lateinit var mAdapter: TabAdapter
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mDream: Dream

    companion object {
        const val EXTRA_DREAM_ID = "android.dream.materialization.dream_id"

        fun newIntent(packageContext: Context, dream: Dream): Intent {
            val intent = Intent(packageContext, TabPagerActivity::class.java)
            intent.putExtra(EXTRA_DREAM_ID, dream.mId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dreamId = intent.getSerializableExtra(EXTRA_DREAM_ID) as UUID
        DreamLab.getItem(dreamId)?.let {
            mDream = it
        }
        setContentView(R.layout.activity_tab_pager)

        mViewPager = findViewById(R.id.viewPager)
        setupViewPager()
        mTabLayout = findViewById(R.id.tabLayout)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun setupViewPager() {
        mAdapter = TabAdapter(supportFragmentManager)
        mAdapter.addFragment(PictureFragment.getInstance(mDream), resources.getString(R.string.picture_tab_title))
        mAdapter.addFragment(DreamInfoFragment.getInstance(mDream), resources.getString(R.string.info_tab_title))
        mAdapter.addFragment(TasksFragment.getInstance(mDream), resources.getString(R.string.tasks_tab_title))
        mViewPager.adapter = mAdapter
    }
}