package com.example.adminfoodexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.adminfoodexpress.databinding.ActivityAdminProfileBinding
import com.example.adminfoodexpress.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference= database.reference.child("user")

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.saveInformationBtn.setOnClickListener { 
            updateUserData()
        }
        binding.name.isEnabled = false
        binding.address.isEnabled = false
        binding.email.isEnabled = false
        binding.phoneno.isEnabled = false
        binding.password.isEnabled = false
        binding.saveInformationBtn.isEnabled = false

        var isEnable = false
        binding.editBtn.setOnClickListener {
            isEnable =! isEnable
            binding.name.isEnabled = isEnable
            binding.address.isEnabled = isEnable
            binding.email.isEnabled = isEnable
            binding.phoneno.isEnabled = isEnable
            binding.password.isEnabled = isEnable
            if(isEnable){
                binding.name.requestFocus()
            }
            binding.saveInformationBtn.isEnabled=isEnable

        }
        retrieveUserData()
    }

    

    private fun retrieveUserData() {
        val currentUserId =auth.currentUser?.uid
        if (currentUserId !=null){
            val userReference =adminReference.child(currentUserId)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName = snapshot.child("nameOwner").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()

                        setDataToTextView(ownerName,email,password,address,phone)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.password.setText(password.toString())
        binding.address.setText(address.toString())
        binding.phoneno.setText(phone.toString())

    }
    private fun updateUserData() {
        var updateName = binding.name.text.toString()
        var updateEmail = binding.email.text.toString()
        var updatePassword =binding.password.text.toString()
        var updateAddress=binding.address.text.toString()
        var updatePhone = binding.phoneno.text.toString()

        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null){
            val userReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            // update the email and password for firebase authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }else {
            Toast.makeText(this, "Profile update failed", Toast.LENGTH_SHORT).show()
        }
    }


}