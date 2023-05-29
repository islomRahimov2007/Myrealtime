package com.example.myrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myrealtime.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val i = Intent(this,Second::class.java)
                        startActivity(i)
                        finish()
                    }else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.Acount.setOnClickListener{
                val i = Intent(this,Second::class.java)
                startActivity(i)
                finish()
            }
        }


    }
}