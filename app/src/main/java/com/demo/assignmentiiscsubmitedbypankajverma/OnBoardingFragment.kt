package com.demo.assignmentiiscsubmitedbypankajverma

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class OnBoardingFragment(private val page: Page) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_on_boarding, container, true)
        view.setBackgroundColor(Color.parseColor(page.color))
        val titleText = view.findViewById<TextView>(R.id.titleText)
        val descText = view.findViewById<TextView>(R.id.descText)
        val imageView = view.findViewById<ImageView>(R.id.imageLogo)

        titleText.text = page.title
        descText.text = page.desc
        imageView.setImageResource(page.image)

        return view
    }


}