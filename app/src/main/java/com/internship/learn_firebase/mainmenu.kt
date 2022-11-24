package com.internship.learn_firebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class mainmenu: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.signout_button).setOnClickListener {
            setContentView(R.layout.activity_main)
        }
    }
}