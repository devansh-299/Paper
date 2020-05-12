package com.devansh.paper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.InputStream


class ImageHelper {

    companion object {
        fun showImage(context: Context, byteArray: ByteArray, imageView: ImageView,
                      circleCrop: Boolean = true) {
            if (circleCrop) {
                Glide.with(context)
                    .load(byteArray)
                    .circleCrop()
                    .into(imageView)
            } else {
                Glide.with(context)
                    .load(byteArray)
                    .centerCrop()
                    .into(imageView)
            }
        }

        fun covertUriToByteArray(context: Context, uri: Uri): ByteArray {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bmp = BitmapFactory.decodeStream(inputStream)
            val byteBuffer = ByteArrayOutputStream()
            val compressedBitmap = Bitmap.createScaledBitmap(bmp, 512, 512, true)
            compressedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteBuffer)
            return byteBuffer.toByteArray()
        }
    }
}