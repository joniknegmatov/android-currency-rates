package io.jonibek.currency.util

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer


fun Date.prettyText() : String{
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    return sdf.format(this)
}

fun RecyclerView.disableAnimation(){
    (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}