package com.devmind.devoxx

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmind.devoxx.model.Speaker
import kotlinx.android.synthetic.main.activity_speaker.*
import java.util.*

class SpeakerActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker)

        val speakerDao = devoxxApplication.speakerDao()
        val speakerUiid = intent.getStringExtra("ID")

        speakerCountry.apply {
            val countries = Locale.getISOCountries().map { Locale("", it) }.map { it.getDisplayCountry() }
            setAdapter(ArrayAdapter(baseContext, android.R.layout.simple_dropdown_item_1line, countries))
        }

        val model = ViewModelProviders.of(this).get(SpeakerViewModel::class.java)

        model.speakerLiveData.observe(this, Observer<Speaker> {
            speakerFirstname.setText(it.firstname)
            speakerLastname.setText(it.lastname)
            speakerCountry.setText(it.country)
        })

        if (!speakerUiid.isNullOrBlank()) {
            model.loadSpeaker(speakerUiid)
        }

        buttonSpeakerSave.setOnClickListener {
            if (speakerFirstname.text.isNullOrBlank() || speakerLastname.text.isNullOrBlank()) {
                Toast.makeText(applicationContext, R.string.speaker_error_required, Toast.LENGTH_LONG).show()
            } else {
                if (speakerUiid.isNullOrBlank()) {
                    model.createSpeaker(
                        Speaker(
                            speakerFirstname.text.toString(),
                            speakerLastname.text.toString(),
                            speakerCountry.text.toString()
                        )
                    )

                } else {
                    model.updateSpeaker(
                        Speaker(
                            speakerFirstname.text.toString(),
                            speakerLastname.text.toString(),
                            speakerCountry.text.toString(),
                            speakerUiid
                        )
                    )
                }
                startActivity(Intent(applicationContext, SpeakerListActivity::class.java))
            }
        }
    }


    override fun onStop() {
        buttonSpeakerSave.setOnClickListener(null)
        super.onStop()
    }
}
