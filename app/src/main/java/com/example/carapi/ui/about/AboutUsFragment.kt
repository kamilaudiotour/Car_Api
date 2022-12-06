package com.example.carapi.ui.about

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carapi.R
import com.example.carapi.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private lateinit var binding: FragmentAboutUsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        // animated background of each item layout
        setAnimationDrawables()

        // wait for button to be clicked and create map intent with
        toMaps()



        return binding.root
    }


    private fun toMaps(){
        binding.showMapBtn.setOnClickListener {

            val gmmIntentUri= Uri.parse("geo:0,0?q=3+Polanka+Poznan")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun setAnimationDrawables(){
        val addressAnimDrawable = binding.addressLayout.background as AnimationDrawable
        addressAnimDrawable.apply {
            setEnterFadeDuration(30)
            setExitFadeDuration(5000)
            start()
        }
        val contactAnimDrawable = binding.contactLayout.background as AnimationDrawable
        contactAnimDrawable.apply {
            setEnterFadeDuration(30)
            setExitFadeDuration(5000)
            start()
        }
        val aboutUsAnimDrawable = binding.aboutUsLayout.background as AnimationDrawable
        aboutUsAnimDrawable.apply {
            setEnterFadeDuration(30)
            setExitFadeDuration(5000)
            start()
        }

    }
}