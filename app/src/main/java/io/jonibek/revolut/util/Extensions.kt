package io.jonibek.revolut.util

import java.text.SimpleDateFormat
import java.util.*


fun Date.prettyText() : String{
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    return sdf.format(this)
}