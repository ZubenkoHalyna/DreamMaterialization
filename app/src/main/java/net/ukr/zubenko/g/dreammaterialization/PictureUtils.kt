package net.ukr.zubenko.g.dreammaterialization

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.y
import android.R.attr.x
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object PictureUtils {
    const val PICTURE_DIR = "net.ukr.zubenko.g.dreammaterialization.fileprovider"

    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        // Чтение размеров изображения на диске
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        // Вычисление степени масштабирования
        var inSampleSize = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth
            inSampleSize = Math.round(
                if (heightScale > widthScale)
                    heightScale
                else
                    widthScale
            )
        }
        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize
        // Чтение данных и создание итогового изображения
        return BitmapFactory.decodeFile(path, options)
    }

    fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return getScaledBitmap(path, size.x, size.y)
    }

    fun getBitmap(path: String): Bitmap {
        return BitmapFactory.decodeFile(path, BitmapFactory.Options())
    }

    fun getPicture(activity: Activity, image: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity.contentResolver, image)
    }

    fun savePicture(bitmap: Bitmap, file: File) {
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}