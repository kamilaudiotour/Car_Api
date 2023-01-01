package com.example.carapi.ui.project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.ProjectsListAdapter
import com.example.carapi.databinding.FragmentProjectsBinding
import com.example.carapi.ui.project.ProjectViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProjectsFragment : Fragment(R.layout.fragment_projects) {

    private val viewModel: ProjectViewModel by activityViewModels()
    private lateinit var binding: FragmentProjectsBinding
    private lateinit var projectsAdapter: ProjectsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsBinding.inflate(inflater, container, false)

        hideBottomNav()
        hideFabWhenUserIsNotAdmin()

        loadData()
        setUpRv()

        binding.apply {
            addProjectFab.setOnClickListener {
                findNavController().navigate(R.id.action_projectsFragment_to_addProjectDialogFragment)
            }
        }

        return binding.root
    }

    private fun loadData() {
        viewModel.projects.observe(viewLifecycleOwner) { projects ->
            viewModel.getProjects()
            Log.d("frag proj", projects.toString())
            projectsAdapter.submitList(projects)
        }
    }

    private fun hideFabWhenUserIsNotAdmin() {
        binding.addProjectFab.visibility = View.GONE
        viewModel.isUserAdmin()
        viewModel.isUserAdmin.observe(viewLifecycleOwner) { isAdmin ->

            if (!isAdmin) {
                binding.addProjectFab.visibility = View.GONE
            } else {
                binding.addProjectFab.visibility = View.VISIBLE
            }
        viewModel.isUserAdmin()
        }

    }


    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }

    private fun setUpRv() {
        projectsAdapter = ProjectsListAdapter()
        binding.projectsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = projectsAdapter
            setHasFixedSize(true)
        }
    }

}