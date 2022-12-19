package com.example.carapi.repository.project

import android.net.Uri
import android.util.Log
import com.example.carapi.models.Project
import com.example.carapi.util.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProjectRepository {

    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun addProject(uri: Uri, project: Project) {
        val imagesRef = storage.reference.child("images/${uri.lastPathSegment}")
        val uploadTask = imagesRef.putFile(uri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d("url task error", task.exception.toString())
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result
                project.photoUrl = downloadUrl.toString()
                db.collection("projects").add(project)
            }

        }


    }

    override suspend fun getProjects(): List<Project> {
        val projectList = mutableListOf<Project>()
        val projectRef = db.collection("projects")
        val snapshot = projectRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val project = it.toObject(Project::class.java)
            if (project != null) {
                projectList.add(project)
                Log.d("firebase listen", projectList.toString())
            }
        }

        return projectList

    }
}