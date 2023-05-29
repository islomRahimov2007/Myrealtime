package com.example.myrealtime

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrealtime.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Second : AppCompatActivity() {
    private lateinit var binding: AppCompatActivity
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase:FirebaseDatabase
    private lateinit var storageRef:
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding =
            setContentView(binding.root)    private var imageUri:Uri?=null

        firebaseAuth = FirebaseAuth.getInstance()
            firebaseDatabase = FirebaseDatabase.getInstance()


            binding.regImg.setImageResource(R.drawable.baseline_add_a_photo_24)

            firebaseDatabase = FirebaseDatabase.getInstance()
            storageRef = FirebaseStorage.getInstance().getReference("ImageReg")

            binding.regImg.setOnClickListener {

                resultLauncher.launch("image/*")

            }


            binding.button2.setOnClickListener {
                funUpload()
            }

        }

        private fun funUpload() {

            val name = binding.username.text.toString()
            val email = binding.login.text.toString()
            val password = binding.pass.text.toString()
            val aginpassword = binding.againpass.text.toString()
            val image = binding.regImg.drawable
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()&&aginpassword.isNotEmpty() && image.isVisible) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val i = Intent(this,MainActivity3::class.java)
                        startActivity(i)
                        finish()
                    }else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                binding.progressBar.visibility = View.VISIBLE
                storageRef = storageRef.child(System.currentTimeMillis().toString())
                imageUri?.let { it ->
                    storageRef.putFile(it).addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            storageRef.downloadUrl.addOnCompleteListener { uri ->
                                val map = HashMap<String, Any>()
                                map["Image"] = uri.toString()
                                map["Name"] = name
                                map["email"] = email
                                map["password"] = password
                                map ["againpassword"] = aginpassword
                                firebaseDatabase.reference.child("Data").setValue(map)

                                    .addOnCompleteListener { taskIt ->
                                        if (taskIt.isSuccessful) {
                                            binding.progressBar.visibility = View.GONE
                                            Toast.makeText(this, "Upload image", Toast.LENGTH_SHORT)
                                                .show()
                                        } else {
                                            binding.progressBar.visibility = View.GONE
                                            Toast.makeText(
                                                this,
                                                taskIt.exception?.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                binding.progressBar.visibility = View.GONE
                                binding.regImg.setImageResource(R.drawable.baseline_add_a_photo_24)


                            }

                        } else {

                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }


                }


            }else{
                Toast.makeText(this, "data invalid or Empty email and password either name", Toast.LENGTH_SHORT).show()
            }
        }

        private val resultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {

            imageUri = it
            binding.regImg.setImageURI(it)

        }




    }

