package net.ukr.zubenko.g.dreammaterialization.fragments

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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
    private lateinit var mDeleteDream: FloatingActionButton

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
        val bitmap = PictureUtils.getScaledBitmap(DreamLab.getPictureFile(mDream).path, requireActivity())
        mImageView.setImageBitmap(bitmap)

        mDeleteDream =  view.findViewById(R.id.deleteDream)
        mDeleteDream.setOnClickListener {
            if(::mDream.isInitialized) {
                DreamLab.deleteRecursively(mDream)
                requireActivity().setResult(Activity.RESULT_CANCELED)
                requireActivity().onBackPressed()
            }
        }

        return view
    }
}