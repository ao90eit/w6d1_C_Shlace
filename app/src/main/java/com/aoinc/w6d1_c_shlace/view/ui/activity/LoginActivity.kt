package com.aoinc.w6d1_c_shlace.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.model.data.NewUser
import com.aoinc.w6d1_c_shlace.view.ui.fragment.SignupFragment
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), SignupFragment.SignUpDelegate {

    private val signupFragment = SignupFragment()

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.user_name_editText)
        passwordEditText = findViewById(R.id.password_editText)
        loginButton = findViewById(R.id.login_button)
        signUpTextView = findViewById(R.id.sign_up_textView)

        FirebaseAuth.getInstance().currentUser?.let {
            if (it.isEmailVerified)
                openProfileActivity()
            else
                showToast("Please verify email address.")
        }

        loginButton.setOnClickListener {
            if (checkInput()) {
                val emailAddress = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true)
                                openProfileActivity()
                            // user has successfully logged in
                        } else {
                            showToast(it.exception?.localizedMessage ?: "Error logging in.")
                        }
                    }
            }
        }

        signUpTextView.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                .add(R.id.sign_up_frame, signupFragment)
                .addToBackStack(signupFragment.tag)
                .commit()
        }
    }

    private fun openProfileActivity() {
        startActivity(Intent(this, ProfileActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun checkInput(): Boolean {
        when {
            usernameEditText.text.toString().trim().isEmpty() -> {
                showToast("User name cannot be empty")
                return false
            }
            passwordEditText.text.toString().trim().isEmpty() -> {
                showToast("Password cannot be empty")
                return false
            }
        }

        return true
    }

    override fun signUpNewUser(newUser: NewUser) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(newUser.userEmail, newUser.userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG_X", "sign up successful!")
                    if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true)
                        openProfileActivity()
                    else
                        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                } else {
                    showToast(it.exception?.localizedMessage ?: "Sign up failed. Try again. :P")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /* Activity Launch Modes:
    *
    *
    *
    */
}