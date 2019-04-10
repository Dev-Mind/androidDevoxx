package com.devmind.devoxx

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmind.devoxx.model.Speaker
import com.devmind.devoxx.model.SpeakerAdapter
import kotlinx.android.synthetic.main.activity_speaker_list.*

interface SpeakerSelectionListener{
    fun onSpeakerSelect(id: String)
}

class SpeakerListActivity : MainActivity(), SpeakerSelectionListener {

    override fun onSpeakerSelect(id: String) {
        startActivity(Intent(applicationContext, SpeakerActivity::class.java).putExtra("ID", id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_list)

        speakerList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = SpeakerAdapter(this@SpeakerListActivity)
        }

        val model = ViewModelProviders.of(this).get(SpeakerListViewModel::class.java)

        model.speakersLiveData.observe(this, Observer<List<Speaker>>{
            (speakerList.adapter as SpeakerAdapter).updateData(it)
        })

        buttonAddSpeaker.setOnClickListener {
            onSpeakerSelect("")
        }
    }

    override fun onStop() {
        buttonAddSpeaker.setOnClickListener(null)
        super.onStop()
    }
}
