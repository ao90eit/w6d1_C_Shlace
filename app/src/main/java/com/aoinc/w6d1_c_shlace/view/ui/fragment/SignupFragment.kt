package com.aoinc.w6d1_c_shlace.view.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.model.data.NewUser
import com.aoinc.w6d1_c_shlace.view.ui.activity.LoginActivity

class SignupFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var verifyUsernameEditText: EditText
    private lateinit var verifyPasswordEditText: EditText

    private lateinit var signUpButton: Button

    private lateinit var signUpDelegate: SignUpDelegate

    interface SignUpDelegate {
        fun signUpNewUser(newUser: NewUser)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpDelegate = (context as LoginActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.sign_up_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.su_user_name_editText)
        passwordEditText = view.findViewById(R.id.su_password_editText)
        verifyUsernameEditText = view.findViewById(R.id.su_verify_user_name_editText)
        verifyPasswordEditText = view.findViewById(R.id.su_verify_password_editText)

        signUpButton = view.findViewById(R.id.su_button)
        signUpButton.setOnClickListener {
            if (checkInput()){
                val newUser = NewUser(usernameEditText.text.toString().trim(), passwordEditText.text.toString().trim())
                signUpDelegate.signUpNewUser(newUser)
                clearTextFields()
                fragmentManager?.popBackStack()
            }
        }
    }

    private fun clearTextFields() {
        usernameEditText.text.clear()
        verifyUsernameEditText.text.clear()
        passwordEditText.text.clear()
        verifyPasswordEditText.text.clear()
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
            usernameEditText.text.toString().trim() != verifyUsernameEditText.text.toString().trim() -> {
                showToast("User names do not match")
                return false
            }
            passwordEditText.text.toString().trim() != verifyPasswordEditText.text.toString().trim() -> {
                showToast("Passwords do not match")
                return false
            }
        }
        return true
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()
        }
    }
}