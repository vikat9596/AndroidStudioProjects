package com.example.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.instagram.Models.User
import com.example.instagram.Utils.USER_NODE
import com.example.instagram.Utils.USER_PROFILE_FOLDER
import com.example.instagram.Utils.uploadImage
import com.example.instagram.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it==null){

                }else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //multi color text
        val text = "<font color=#FF000000>Already have an Account? </font> <font color=#2292EC>Login</font>"
        binding.alreadyHaveAccount.setText(Html.fromHtml(text))

        user = User()

        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1){

                binding.signUpBtn.text = "Update Profile"
                binding.alreadyHaveAccount.isVisible= false
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)

                        }
                        //update name email password, currently not updating
                        binding.name.editText?.setText(user.name)
                        binding.email.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)


                    }
            }
        }

        binding.signUpBtn.setOnClickListener {

            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                            finish()
                        }
                }
            } else{
                //here
            if (binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please fill all the details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = binding.name.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()

                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                                finish()
                            }

                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }}
        }
        binding.addImage.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finish()
        }
    }
}