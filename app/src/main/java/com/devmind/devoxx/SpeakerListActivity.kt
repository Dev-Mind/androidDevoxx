package com.devmind.devoxx

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        val speakerDao = devoxxApplication.speakerDao()

        speakerList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = SpeakerAdapter(this@SpeakerListActivity)
            (adapter as SpeakerAdapter).updateData(speakerDao.readAll())
        }

        buttonAddSpeaker.setOnClickListener {
            onSpeakerSelect("")
        }
    }

    override fun onStop() {
        buttonAddSpeaker.setOnClickListener(null)
        super.onStop()
    }
}
