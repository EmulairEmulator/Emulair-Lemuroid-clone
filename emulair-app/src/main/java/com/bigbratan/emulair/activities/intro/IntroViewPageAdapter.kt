package com.bigbratan.emulair.activities.intro

import com.bigbratan.emulair.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.labawsrh.aws.introscreen.ScreenItem

class IntroViewPagerAdapter(var mContext: Context, mListScreen: List<ScreenItem>) :
    PagerAdapter() {
    var mListScreen: List<ScreenItem>

    init {
        this.mListScreen = mListScreen
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen: View = inflater.inflate(R.layout.layout_screen, null)
        container.addView(layoutScreen)
        return layoutScreen
    }

    override fun getCount(): Int {
        return mListScreen.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, object: Any) {
        container.removeView(object as View)
    }
}
