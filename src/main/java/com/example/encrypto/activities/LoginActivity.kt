package com.example.encrypto.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.encrypto.cryptography.PasswordEncryption
import com.example.encrypto.databinding.ActivityLoginBinding
import com.example.encrypto.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // button sign in on click
        binding.btnSignIn.setOnClickListener {

            binding.btnSignIn.visibility = INVISIBLE
            binding.progressBar2.visibility = VISIBLE

            // store input data
            val inputEmail = binding.signInEmail.text.toString().trim()
            val inputPassword = binding.signInPassword.text.toString().trim()

            // if input data is valid sign in the user with encrypted password
            if (isDataValid(inputEmail, inputPassword)) {
                val encryptedPassword = PasswordEncryption.getHash(inputPassword, Constants.ALGORITHM)
                signInUser(inputEmail, encryptedPassword)
            } else {
                binding.btnSignIn.visibility = VISIBLE
                binding.progressBar2.visibility = INVISIBLE
            }
        }

        // text view on click
        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    // sign in existing user
    private fun signInUser(email: String, password: String) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener {

            val token = it.result

            val map: HashMap<String, Any> = HashMap()
            map["token"] = token!!

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_KEY)
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .updateChildren(map)
                                .addOnSuccessListener {

                                    // if success redirect to next activity
                                    Constants.showSnackBar(this, "Sign in was successful", binding.root)
                                    binding.btnSignIn.visibility = VISIBLE
                                    binding.progressBar2.visibility = INVISIBLE

                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()

                                }
                    }
                    .addOnFailureListener {
                        Constants.showSnackBar(this, "ERROR : ${it.message.toString()}", binding.root)
                        binding.btnSignIn.visibility = VISIBLE
                        binding.progressBar2.visibility = INVISIBLE
                    }
        }


    }

    // check if input data is valid
    private fun isDataValid(email: String, password: String): Boolean {

        if (email.isEmpty() || password.isEmpty()) {
            Constants.showSnackBar(this, "Empty field detected", binding.root)
            return false
        }

        return true
    }

}