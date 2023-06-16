package com.bigbratan.emulair.activities.account

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.bigbratan.emulair.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        setSupportActionBar(findViewById(R.id.toolbar))

        val buttonBack: ImageButton = findViewById(R.id.back)
        buttonBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.slide_out_right)
    }
}
