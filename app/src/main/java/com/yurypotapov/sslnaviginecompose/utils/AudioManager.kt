package com.yurypotapov.sslnaviginecompose.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.view.SoundEffectConstants

class AudioManager(private val context: Context) {
    @SuppressLint("WrongConstant")
    public fun soundEffect() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN,1.0f)
    }
}