package com.devmind.devoxx

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmind.devoxx.model.SpeakerAdapter
import kotlinx.android.synthetic.main.activity_speaker_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface SpeakerSelectionListener{
    fun onSpeakerSelect(id: String)
}

class SpeakerListActivity : MainActivity(), SpeakerSelectionListener, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

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

        launch {
            val speakers = devoxxApplication.speakerDao().readAll()
            withContext(Dispatchers.Main){
                (speakerList.adapter as SpeakerAdapter).updateData(speakers)
            }
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
