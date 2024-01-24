package com.example.adminfoodexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfoodexpress.databinding.ActivitySignupBinding
import com.example.adminfoodexpress.model.UserModel
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var username : String
    private lateinit var nameOfRestaurant : String
    private lateinit var database : DatabaseReference

    private val binding:ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //init firebase auth
        auth = Firebase.auth
        //init firebase database
        database= Firebase.database.reference



        binding.createAccountBtn.setOnClickListener {
            //get text from edittext
            email = binding.emailOrPhone.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            username = binding.nameOwner.text.toString().trim()
            password = binding.password.text.toString().trim()

            if(username.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            } else{
                createAccount(email,password)
            }


        }
        binding.alreadyHaveAccountBtn.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val locationList = arrayOf("Nagpur","Bhandara","Mumbai","Pune")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully!",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Acoount creation failed!",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }
    //save data into database
    private fun saveUserData() {
        //get text from edittext
        email = binding.emailOrPhone.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        username = binding.nameOwner.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(email,password,username,nameOfRestaurant)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data in firebase database
        database.child("user").child(userId).setValue(user)
    }
}