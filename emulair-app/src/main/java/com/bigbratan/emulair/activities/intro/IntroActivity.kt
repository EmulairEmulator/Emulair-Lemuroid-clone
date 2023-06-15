package com.bigbratan.emulair.activities.intro

import com.bigbratan.emulair.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bigbratan.emulair.activities.main.MainActivity
import com.google.android.material.tabs.TabLayout

class IntroActivity : AppCompatActivity() {
    private var screenPager: ViewPager? = null
    var introViewPagerAdapter: IntroViewPagerAdapter? = null
    var tabIndicator: TabLayout? = null
    var btnNext: Button? = null
    var position = 0
    var btnGetStarted: Button? = null
    var btnAnim: Animation? = null
    var tvSkip: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
        setContentView(R.layout.activity_intro)
        supportActionBar!!.hide()

        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        tvSkip = findViewById(R.id.tv_skip)

        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Fresh Food",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img1
            )
        )
        mList.add(
            ScreenItem(
                "Fast Delivery",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img2
            )
        )
        mList.add(
            ScreenItem(
                "Easy Payment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img3
            )
        )

        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screenPager?.adapter = introViewPagerAdapter
        tabIndicator?.setupWithViewPager(screenPager)
        btnNext?.setOnClickListener {
            position = screenPager?.currentItem!!
            if (position < mList.size) {
                position++
                screenPager?.currentItem = position
            }
            if (position == mList.size - 1) {
                loaddLastScreen()
            }
        }
        tabIndicator?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        btnGetStarted?.setOnClickListener {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            savePrefsData()
            finish()
        }
        tvSkip?.setOnClickListener { screenPager?.currentItem = mList.size }
    }

    private fun restorePrefData(): Boolean {
        val pref =
            applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpened", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()
    }

    private fun loaddLastScreen() {
        btnNext!!.visibility = View.INVISIBLE
        btnGetStarted!!.visibility = View.VISIBLE
        tvSkip!!.visibility = View.INVISIBLE
        tabIndicator!!.visibility = View.INVISIBLE
        btnGetStarted!!.animation = btnAnim
    }
}
