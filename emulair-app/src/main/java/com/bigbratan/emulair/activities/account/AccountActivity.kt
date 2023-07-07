package com.bigbratan.emulair.activities.account

import android.os.Bundle
import android.widget.ImageButton
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.retrograde.RetrogradeAppCompatActivity

class AccountActivity : RetrogradeAppCompatActivity() {

    override fun getActivityName(): String {
        return "AccountActivity"
    }

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
    }
}
