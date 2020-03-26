package net.ukr.zubenko.g.dreammaterialization.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import net.ukr.zubenko.g.dreammaterialization.PictureUtils
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream

class PictureFragment: Fragment() {
    private lateinit var mDream: Dream
    private lateinit var mImageView: ImageView

    companion object {
        fun getInstance(dream: Dream): Fragment {
            val fr = PictureFragment()
            fr.mDream = dream
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.picture_fragment, container, false)

        mImageView = view.findViewById(R.id.imageView)
        val bitmap = PictureUtils.getBitmap(DreamLab.getPictureFile(mDream).path)
        mImageView.setImageBitmap(bitmap)

        return view
    }
}