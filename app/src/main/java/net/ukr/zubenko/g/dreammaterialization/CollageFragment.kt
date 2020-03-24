package net.ukr.zubenko.g.dreammaterialization

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.*
import net.ukr.zubenko.g.dreammaterialization.PictureUtils.PICTURE_DIR
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamViewLab
import java.io.File

class CollageFragment: Fragment() {
    private lateinit var collage: Collage
    private lateinit var mPictureFile: File
    private var mDreamAdding: Dream? = null
    private val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    companion object {
        const val REQUEST_PHOTO = 0
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
        val dream = Dream()
        mDreamAdding = dream
        mPictureFile = DreamLab.getPictureFile(dream)
        val uri = FileProvider.getUriForFile(requireActivity(), PICTURE_DIR, DreamLab.getPictureFile(dream))
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        val cameraActivities = requireActivity().packageManager.queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
        for (activity in cameraActivities) {
            requireActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        startActivityForResult(captureImage, REQUEST_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            REQUEST_PHOTO -> {
                if (mPictureFile.exists()) {
                    mDreamAdding?.let { dream ->
                        val dreamView = DreamView(
                            dream.mId,
                            100,
                            100,
                            collage.width / 3,
                            collage.height / 3,
                            0
                        )
                        val bitmap = PictureUtils.getScaledBitmap(mPictureFile.path, dreamView.mWidth, dreamView.mHeight)
                        collage.mDreamViews[dreamView] = bitmap
                        DreamLab.add(dream)
                        DreamViewLab.add(dreamView)
                        collage.invalidate()
                    }
                }
                mDreamAdding = null
            }
        }
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