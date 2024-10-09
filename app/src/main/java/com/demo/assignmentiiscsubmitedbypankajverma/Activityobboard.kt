package com.demo.assignmentiiscsubmitedbypankajverma

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Activityobboard : AppCompatActivity() {

    private val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            when(position) {
                0 ->{
                    skipBtn.text = "Skip"
                    skipBtn.visible()
                    nextBtn.visible()
                    previousbtn.gone()
                }
                pagerList.size - 1 ->{
                    skipBtn.text = "GetStarted"
                    skipBtn.visible()
                    nextBtn.gone()
                    previousbtn.visible()
                }
                else ->{
                    skipBtn.text = "Skip"
                    skipBtn.visible()
                    nextBtn.visible()
                    previousbtn.visible()
                }
            }
        }
    }

    private val pagerList = arrayListOf(
        Page("Screen1",R.drawable.iisc,"HI THIS IS PANKAJ","#66a6ff"),
        Page("Screen2",R.drawable.iisc,"This assignment is given by IICS","#66a6ff"),
        Page("Screen3",R.drawable.iisc,"let's get started....","#66a6ff")
    )

    lateinit var onBoardingViewPager2: ViewPager2
    lateinit var skipBtn: Button
    lateinit var nextBtn : Button
    lateinit var previousbtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activityobboard)

        onBoardingViewPager2 = findViewById(R.id.onBoardingViewPage2)
        skipBtn = findViewById(R.id.skipbtn)
        nextBtn = findViewById(R.id.nextbtn)
        previousbtn = findViewById(R.id.previousbtn)

        onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@Activityobboard,pagerList)
            registerOnPageChangeCallback(onBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)
        TabLayoutMediator(tabLayout,onBoardingViewPager2){tab, position -> }.attach()

        nextBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem < onBoardingViewPager2.adapter!!.itemCount- 1) {
                onBoardingViewPager2.currentItem += 1
            } else {
                homeScreenIntent()
            }
        }

        skipBtn.setOnClickListener {
            homeScreenIntent()
        }
        previousbtn.setOnClickListener {
            if(onBoardingViewPager2.currentItem >0) {
                onBoardingViewPager2.currentItem -= 1
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        onBoardingViewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val homeIntent = Intent(this,MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}