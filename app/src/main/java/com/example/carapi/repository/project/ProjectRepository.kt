package com.example.carapi.repository.project

import android.net.Uri
import com.example.carapi.models.Project

interface ProjectRepository {

    fun addProject(uri: Uri, project: Project)
    suspend fun getProjects(): List<Project>
}