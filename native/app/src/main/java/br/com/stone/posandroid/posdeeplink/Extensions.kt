package br.com.stone.posandroid.posdeeplink

import android.content.Context
import androidx.annotation.RawRes

fun Context.fromResource(@RawRes raw: Int): String {
    return this.resources.openRawResource(raw).use {
        val size = it.available()
        val buffer = ByteArray(size)
        it.read(buffer)

        String(buffer, Charsets.UTF_8)
    }
}
