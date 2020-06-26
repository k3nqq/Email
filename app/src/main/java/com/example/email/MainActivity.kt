package com.example.email


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.email.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.security.auth.Subject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener { sendMail() }

        idBtnGetPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        btnTakePhoto.setOnClickListener {
            val imageTakeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(imageTakeIntent, 1)
        }

    }

    private fun sendMail() {
        val recipientList: String = edtTo.text.toString()
        val recipients = recipientList.split(",").toTypedArray()

        val subject: String = edtSubject.text.toString()
        val message: String = edtMessage.text.toString()

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "message/rfc822"

        startActivity(
                Intent.createChooser(
                        intent,
                        "Через какого почтового клиента отправить письмо?"
                )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap?
            idImageViewPhoto.setImageBitmap(bitmap)
        }
    }
}
