package com.example.news.common

import android.util.Log

interface Logger {
    fun d(tag: String, message: String)
}

fun AndroidLogcatLogger(): Logger = object :Logger{
    override fun d(tag: String, message: String) {
        Log.d(tag,message)
    }

}