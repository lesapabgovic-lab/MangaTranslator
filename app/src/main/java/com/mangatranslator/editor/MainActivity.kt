package com.mangatranslator.editor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var originalText: EditText
    private lateinit var translationText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(18, 18, 18))
            setPadding(24, 24, 24, 24)
        }

        val title = TextView(this).apply {
            text = "Manga Translator 0.2"
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val openButton = Button(this).apply {
            text = "Открыть страницу"
            setOnClickListener { openImage() }
        }

        imageView = ImageView(this).apply {
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
            setBackgroundColor(Color.BLACK)
        }

        val ocrButton = Button(this).apply {
            text = "🔎 Распознать текст (OCR)"
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "OCR подключим следующим этапом",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        originalText = EditText(this).apply {
            hint = "Оригинальный текст"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            minLines = 3
            gravity = Gravity.TOP
        }

        translationText = EditText(this).apply {
            hint = "Русский перевод"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            minLines = 3
            gravity = Gravity.TOP
        }

        val saveButton = Button(this).apply {
            text = "Сохранить перевод"
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Перевод сохранён",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        root.addView(title, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ))

        root.addView(openButton)

        root.addView(imageView, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0,
            1f
        ))

        root.addView(ocrButton)
        root.addView(originalText)
        root.addView(translationText)
        root.addView(saveButton)

        setContentView(root)
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        startActivityForResult(intent, 100)
    }

    @Deprecated("Deprecated in Android API")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 &&
            resultCode == Activity.RESULT_OK
        ) {
            val uri: Uri? = data?.data

            if (uri != null) {
                imageView.setImageURI(uri)

                Toast.makeText(
                    this,
                    "Страница открыта",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
