package com.devmind.devoxx.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.devmind.devoxx.R
import com.devmind.devoxx.SpeakerSelectionListener

class SpeakerAdapter(val listener: SpeakerSelectionListener): RecyclerView.Adapter<SpeakerAdapter.ViewHolder>(){

    private val speakers = mutableListOf<Speaker>()

    class ViewHolder(view: ConstraintLayout): RecyclerView.ViewHolder(view){
        val name = view.findViewById(R.id.speakerName) as TextView
        val country = view.findViewById(R.id.speakerCountry) as TextView
    }

    fun updateData(speakers: List<Speaker>){
        this.speakers.clear()
        this.speakers.addAll(speakers)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_speaker_item, parent, false)
        return ViewHolder(view as ConstraintLayout)
    }

    override fun getItemCount(): Int = speakers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.apply {
            name.text = "${speaker.firstname} ${speaker.lastname}"
            country.text = speaker.country
            itemView.setOnClickListener {
                listener.onSpeakerSelect(speaker.uuid)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewRecycled(holder)
    }
}


