package com.aoinc.w6d1_c_shlace.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.model.Shlace
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ShlaceAdapter(private var shlaceList: List<Shlace>) : RecyclerView.Adapter<ShlaceAdapter.ShlaceViewHolder>() {

    fun updateShlaceList(newList : List<Shlace>) {
        shlaceList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShlaceViewHolder =
        ShlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shlace_item, parent, false))

    override fun onBindViewHolder(holder: ShlaceViewHolder, position: Int) {
        val shlace = shlaceList[position]

        holder.apply {
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(RequestOptions().centerCrop())
                .load(shlace.photoURL)
                .into(photo)
            postedBy.text = shlace.postedBy
            description.text = "${shlace.description} : located at ${shlace.address}"
        }
    }

    override fun getItemCount(): Int = shlaceList.size

    inner class ShlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.shlace_place_imageView)
        val postedBy: TextView = itemView.findViewById(R.id.posted_by_textView)
        val description: TextView = itemView.findViewById(R.id.description_textView)
//        val address: TextView = itemView.findViewById()
    }
}