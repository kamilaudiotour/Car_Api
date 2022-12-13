package com.example.carapi.ui.project

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Project
import com.example.carapi.repository.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectViewModel @Inject constructor(private val projectRepository: ProjectRepository) :
    ViewModel() {

    private val _projects = MutableLiveData<List<Project>>()
    val projects: LiveData<List<Project>>
        get() = _projects


    fun addProject(uri: Uri, project: Project) {
        projectRepository.addProject(uri, project)
    }

    fun getProjects() {
        viewModelScope.launch {
            _projects.value = projectRepository.getProjects()
        }
    }

}