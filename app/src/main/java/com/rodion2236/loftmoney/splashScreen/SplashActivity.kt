package com.rodion2236.loftmoney.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ActivitySplashBinding
import com.rodion2236.loftmoney.login.LoginActivity
import com.rodion2236.loftmoney.main.MainActivity

class SplashActivity : AppCompatActivity() {

    lateinit var bindingSplashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplashBinding.root)

        val sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0)
        val authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "")
        if (TextUtils.isEmpty(authToken)) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}