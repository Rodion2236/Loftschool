package com.rodion2236.loftmoney.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ActivityLoginBinding
import com.rodion2236.loftmoney.main.MainActivity

class LoginActivity : AppCompatActivity() {

    lateinit var bindingLoginBinding: ActivityLoginBinding
    private var loginViewModel: LoginViewModel? = null
    private var googleClient: GoogleSignInClient? = null
    private val requestCode_sign = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLoginBinding.root)

        configureViews()
        configureViewModel()
        configureGoogleAuthorization()
    }

    private fun configureGoogleAuthorization() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCode_sign) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completed_task: Task<GoogleSignInAccount>?) {
        try {
            val account = completed_task?.getResult(ApiException::class.java)

            if (account != null) {
                loginViewModel!!.makeLogin((application as LoftApp).authApi, account.id)
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.an_error_occurred_on_the_connection,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } catch (e: ApiException) {
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun configureViews() {
        bindingLoginBinding.loginButtonView.setOnClickListener {
            val signInIntent = googleClient!!.signInIntent
            startActivityForResult(signInIntent, requestCode_sign)
        }
    }

    private fun configureViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel!!.messageString.observe(this) { error: String? ->
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
            }
        }

        loginViewModel!!.authToken.observe(this) { authToken: String? ->
            if (!TextUtils.isEmpty(authToken)) {
                val sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0)
                sharedPreferences.edit().putString(LoftApp.AUTH_KEY, authToken).apply()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}