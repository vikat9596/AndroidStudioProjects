package com.example.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodapp.Model.UserModel
import com.example.foodapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //init of firebase auth
        auth = Firebase.auth
        //init firebase database
        database = Firebase.database.reference
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //init of google
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOption)


        //login with email and password
            binding.loginButton.setOnClickListener{
                //get data from text field
                email= binding.email.text.toString().trim()
                password = binding.password.text.toString().trim()

                if(email.isBlank() || password.isBlank()){
                    Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
                }else{
                    createUser()
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                }

        }
        binding.donthave.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if (task.isSuccessful){
                val user =auth.currentUser
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        saveUserData()
                        val user = auth.currentUser
                        updateUi(user)
                    }else{
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun saveUserData() {
        //retrieve data from text field
        email= binding.email.text.toString().trim()
        password =binding.password.text.toString().trim()

        val user = UserModel(email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save data in database
        database.child("user").child(userId).setValue(user)
    }

    private fun updateUi( user: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}