package com.skyblue.storeapplicationspecificdatakotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skyblue.storeapplicationspecificdatakotlin.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {

    //https://developers.google.com/drive/api/guides/appdata
    private lateinit var binding: ActivityMainBinding
    val APP_DATA = "/.Skyblue"
    private var bmp: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Create folder
        val dir = getExternalFilesDir(APP_DATA)
        if (!dir!!.exists()) {
            if (!dir!!.mkdir()) {
                Toast.makeText(
                    applicationContext,
                    "The folder " + dir!!.path + "was not created",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Example store bitmap, to create empty bitmap
        val w = 200
        val h = 200

        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        bmp = Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
        bmp?.eraseColor(Color.RED)
        val canvas = Canvas(bmp!!)

        // Convert bitmap to jpg and save
        convertAndSaveImage()

        // get stored image
        val root = getExternalFilesDir("/")!!.path + "/" + ".Skyblue/"
        val file = File(root, "test_image" + ".jpg")
        val testBitmap = BitmapFactory.decodeFile(file.absolutePath)

        val debug = ""
    }

    private fun convertAndSaveImage() {
        var dir: String? = null
        dir = getExternalFilesDir("/")!!.path + "/" + ".Skyblue/"

        Toast.makeText(this, dir, Toast.LENGTH_LONG).show()

        val bos = ByteArrayOutputStream()
        bmp!!.compress(Bitmap.CompressFormat.JPEG, 80, bos)
        val bitmapdata = bos.toByteArray()

        val file = File(dir, "test_image" + ".jpg")

        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}