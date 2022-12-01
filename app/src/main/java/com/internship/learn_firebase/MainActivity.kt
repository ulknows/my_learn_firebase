package com.internship.learn_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        findViewById<Button>(R.id.signin_button).setOnClickListener {
            val mail = findViewById<EditText>(R.id.editEmail).text.toString()
            val pass = findViewById<EditText>(R.id.editPassword).text.toString()
            Log.d(MAILLOGIN, "user - " + mail + " | " + pass)

            signIn(mail, pass)
        }

        findViewById<Button>(R.id.signout_button).setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(baseContext, "User logout", Toast.LENGTH_SHORT).show()
            updateUI(auth.currentUser)
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val id = findViewById<EditText>(R.id.reg_id).text.toString()
            val mail = findViewById<EditText>(R.id.reg_mail).text.toString()
            val pass = findViewById<EditText>(R.id.reg_pass).text.toString()
            val passconfirm = findViewById<EditText>(R.id.reg_pass_confirm).text.toString()
            if (pass != passconfirm) {
                Toast.makeText(baseContext, "Password not match", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(mail, pass)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with signed-in user
                    Log.d(MAILLOGIN, "User sign in with email : Successful")
                    Toast.makeText(baseContext, "Sign in successful", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Sign in fails, display dialog to the user
                    Log.d(MAILLOGIN, "User sign in with email : Failed")
                    Toast.makeText(baseContext, "Sign in failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun reload() {}

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "User created.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d(MAILREG, "User " + findViewById<EditText>(R.id.reg_id).text.toString() + " created - " + user?.uid)
                    //after user created


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(MAILREG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val signin = findViewById<Button>(R.id.signin_button)
        val signout = findViewById<Button>(R.id.signout_button)
        if (user != null) {
            signin.visibility = View.INVISIBLE
            signin.isEnabled = false

            signout.visibility = View.VISIBLE
            signout.isEnabled = true
        } else {
            signin.visibility = View.VISIBLE
            signin.isEnabled = true

            signout.visibility = View.INVISIBLE
            signout.isEnabled = false
        }
    }

    //
    companion object {
        private const val MAILREG = "REGISTER-MAIL"
        private const val MAILLOGIN = "LOGIN-MAIL"
    }
}