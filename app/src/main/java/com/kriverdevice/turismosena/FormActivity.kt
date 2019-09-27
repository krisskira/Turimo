package com.kriverdevice.turismosena

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kriverdevice.turismosena.application.Constants

class FormActivity : AppCompatActivity() {

    val result = "asdadas"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val button = findViewById<Button>(R.id.cerra)
        button.setOnClickListener {
            val i = intent
            i.putExtra(Constants.sitesKey, "Resultadp")
            setResult(1000, i)
            finish()
        }
    }
}
