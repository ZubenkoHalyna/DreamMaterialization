package net.ukr.zubenko.g.dreammaterialization

import android.support.v4.app.Fragment
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.DbBaseHelper

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        DreamLab.init(applicationContext)
        DbBaseHelper.init(applicationContext)
        return CollageFragment.newInstance()
    }
}
