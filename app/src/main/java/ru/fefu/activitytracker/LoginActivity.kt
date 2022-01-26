package ru.fefu.activitytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun toRegistrationScreen(view: View) {
        val registrationIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationIntent)
    }

    fun toMyActivityScreen(view: View) {
        val activityIntent = Intent(this, BottomNavActivity::class.java)
        startActivity(activityIntent)
    }
}