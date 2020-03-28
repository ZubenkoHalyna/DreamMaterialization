package net.ukr.zubenko.g.dreammaterialization.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DbItem

abstract class DbItemHolder(inflater: LayoutInflater,
                            parent: ViewGroup,
                            type: Int):
    RecyclerView.ViewHolder(inflater.inflate(type, parent, false)) {

    abstract fun bindItem(item: DbItem)
    abstract fun delete()
}