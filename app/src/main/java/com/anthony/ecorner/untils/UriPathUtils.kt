package com.anthony.ecorner.untils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.os.Build
import androidx.loader.content.CursorLoader




class UriPathUtils {


    companion object{
        /**
         * 根据图片的Uri获取图片的绝对路径(已经适配多种API)
         * @return 如果Uri对应的图片存在,那么返回该图片的绝对路径,否则返回null
         */
        fun getRealPathFromUri(context: Context, uri: Uri): String? {
            val sdkVersion = Build.VERSION.SDK_INT
            if (sdkVersion < 11) {
                // SDK < Api11
                return getRealPathFromUri_BelowApi11(context, uri)
            }
            return if (sdkVersion < 19) {
                // SDK > 11 && SDK < 19
                getRealPathFromUri_Api11To18(context, uri)
            } else getRealPathFromUri_AboveApi19(context, uri)
            // SDK > 19
        }

        /**
         * 适配api19以上,根据uri获取图片的绝对路径
         */
        private fun getRealPathFromUri_AboveApi19(context: Context, uri: Uri): String? {
            var cursor = context.getContentResolver().query(uri, null, null, null, null)
            cursor?.moveToFirst()
            var document_id = cursor?.getString(0)
            document_id = document_id?.substring(document_id.lastIndexOf(":") + 1)
            cursor?.close()

            cursor = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", document_id?.let { arrayOf<String>(it) }, null)
            cursor?.moveToFirst()
            val path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            cursor?.close()

            return path
        }

        /**
         * 适配api11-api18,根据uri获取图片的绝对路径
         */
        private fun getRealPathFromUri_Api11To18(context: Context, uri: Uri): String? {
            var filePath: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val loader = CursorLoader(context, uri, projection, null, null, null)
            val cursor = loader.loadInBackground()

            if (cursor != null) {
                cursor.moveToFirst()
                filePath = cursor.getString(cursor.getColumnIndex(projection[0]))
                cursor.close()
            }
            return filePath
        }

        /**
         * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
         */
        private fun getRealPathFromUri_BelowApi11(context: Context, uri: Uri): String? {
            var filePath: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                filePath = cursor.getString(cursor.getColumnIndex(projection[0]))
                cursor.close()
            }
            return filePath
        }
    }



}