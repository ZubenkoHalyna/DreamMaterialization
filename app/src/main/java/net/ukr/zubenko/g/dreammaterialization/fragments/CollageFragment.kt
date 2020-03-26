package net.ukr.zubenko.g.dreammaterialization.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import android.graphics.Bitmap
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView
import android.graphics.Point
import net.ukr.zubenko.g.dreammaterialization.views.Collage
import net.ukr.zubenko.g.dreammaterialization.PictureUtils
import net.ukr.zubenko.g.dreammaterialization.R
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamViewLab


class CollageFragment: Fragment() {
    private lateinit var collage: Collage

    companion object {
        const val REQUEST_PICTURE = 0
        fun newInstance() = CollageFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.collage_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.add_dream -> {
                getPic()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun getPic() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent,
            REQUEST_PICTURE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK)
            return

        when (requestCode) {
            REQUEST_PICTURE -> {
                data?.data?.let { imageUri ->
                    val bitmap =
                        PictureUtils.getPicture(requireActivity(), imageUri)
                    val size = getBitmapSize(bitmap)

                    val dream = Dream()
                    val dreamView = DreamView(dream.mId, 100, 100, size.x, size.y, 0)
                    DreamLab.add(dream)
                    DreamViewLab.add(dreamView)
                    collage.mDreamViews[dreamView] = bitmap
                    collage.invalidate()

                    PictureUtils.savePicture(
                        bitmap,
                        DreamLab.getPictureFile(dream)
                    )
                }
            }
        }
    }

    private fun getBitmapSize(bitmap: Bitmap): Point {
        var width = collage.width / 3
        var height = collage.height / 3

        if (bitmap.width > bitmap.height) {
            width = bitmap.width * height / bitmap.height
        } else {
            height = bitmap.height * width / bitmap.width
        }
        return Point(width, height)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.collage_fragment, container, false)

        collage = view.findViewById(R.id.collage)
        collage.load()

        return view
    }
}