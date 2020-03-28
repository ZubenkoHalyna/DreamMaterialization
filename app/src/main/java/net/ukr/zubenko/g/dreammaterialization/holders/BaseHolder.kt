package net.ukr.zubenko.g.dreammaterialization.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem

abstract class BaseHolder<T: DbItem> (inflater: LayoutInflater,
                                      parent: ViewGroup,
                                      type: Int,
                                      private val openItemInfoActivity: (T)->  Unit):
    DbItemHolder(inflater, parent, type)  {

    init {
        itemView.setOnClickListener(::onClick)
    }

    abstract fun bind(item: T)
    abstract val item: T

    fun onClick(view: View) {
        openItemInfoActivity(item)
    }
}