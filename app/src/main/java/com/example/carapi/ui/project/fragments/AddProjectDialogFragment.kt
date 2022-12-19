package com.example.carapi.ui.project.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.databinding.FragmentAddProjectDialogBinding
import com.example.carapi.models.Project
import com.example.carapi.ui.project.ProjectViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddProjectDialogFragment : DialogFragment(R.layout.fragment_add_project_dialog) {

    private lateinit var binding: FragmentAddProjectDialogBinding
    private val viewModel: ProjectViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProjectDialogBinding.inflate(inflater, container, false)

        hideBottomNav()

        handleGalleryImgPickIntent()


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val background = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(background, 20)
        dialog?.window?.setBackgroundDrawable(inset)
        dialog?.window?.setLayout(
            (LinearLayout.LayoutParams.MATCH_PARENT),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

    }

    private fun handleTextInputsNotEmpty(): Boolean {
        binding.apply {
            return !(titleEt.text.toString().isEmpty() || descriptionEt.text.toString().isEmpty())
        }
    }


    private fun handleGalleryImgPickIntent() {

        val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
            Log.d("returned uri", uri.toString())

            viewModel.addProject(
                uri!!,
                Project(
                    name = binding.titleEt.text.toString(),
                    description = binding.descriptionEt.text.toString()
                )
            )
        }
        binding.addPhotoBtn.setOnClickListener {
            if (handleTextInputsNotEmpty()) {
                getContent.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "Nie wprowadzono danych", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }


}