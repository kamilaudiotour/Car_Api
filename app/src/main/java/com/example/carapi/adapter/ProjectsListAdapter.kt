package com.example.carapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.adapter.ProjectsListAdapter.ProjectsViewHolder
import com.example.carapi.databinding.ItemProjectBinding
import com.example.carapi.models.Project
import com.squareup.picasso.Picasso

class ProjectsListAdapter() :
    ListAdapter<Project, ProjectsViewHolder>(DiffCallback) {

    inner class ProjectsViewHolder(private var binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            binding.apply {
                Log.d("recycler view url", project.photoUrl)
                Picasso.get()
                    .load(project.photoUrl)
                    .into(projectIv)
                titleTv.text = project.name
                descriptionTv.text = project.description
            }

            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(
            ItemProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val project = getItem(position)
        holder.bind(project)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

    }
}