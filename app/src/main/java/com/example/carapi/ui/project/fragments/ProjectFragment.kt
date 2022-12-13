package com.example.carapi.ui.project.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.databinding.FragmentProjectBinding
import com.example.carapi.models.Project
import com.example.carapi.ui.project.ProjectViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProjectFragment : Fragment(R.layout.fragment_project) {

    private lateinit var binding: FragmentProjectBinding
    private val viewModel: ProjectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectBinding.inflate(inflater, container, false)

        hideBottomNav()

        handleGalleryImgPickIntent()


        return binding.root
    }

    private fun handleGalleryImgPickIntent() {
        val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
            Log.d("returned uri", uri.toString())
            viewModel.addProject(uri!!, Project(name = "tojotka", "deskrypcjon"))
        }

        binding.addPhotoBtn.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }
}