package com.devmind.devoxx

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmind.devoxx.model.SpeakerAdapter
import kotlinx.android.synthetic.main.activity_speaker_list.*

class SpeakerListActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_list)

        val speakerDao = devoxxApplication.speakerDao()

        speakerList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = SpeakerAdapter()
            (adapter as SpeakerAdapter).updateData(speakerDao.readAll())
        }
    }
}
