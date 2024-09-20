package com.example.todoapproom.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.todoapproom.R

object SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context) {
        // Si el MediaPlayer ya existe, libéralo antes de crear uno nuevo
        mediaPlayer?.release()

        // Crear un nuevo MediaPlayer para reproducir el sonido
        mediaPlayer = MediaPlayer.create(context, R.raw.dingtask)

        // Iniciar el sonido
        mediaPlayer?.start()

        // Liberar recursos cuando el sonido termine de reproducirse
        mediaPlayer?.setOnCompletionListener {
            it.release()  // Liberar automáticamente cuando termine
            mediaPlayer = null  // Asegurar que la referencia a MediaPlayer se libere
        }
    }
}