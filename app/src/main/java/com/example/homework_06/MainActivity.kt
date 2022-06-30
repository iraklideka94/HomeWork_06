package com.example.homework_06

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework_06.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore

            var activeUser = 0
            var deletedUser = 0


            binding.saveBtn.setOnClickListener {


                binding.deletedUsers.text = deletedUser.toString()

                val firstname = binding.edFirstName.text.toString()
                val lastname = binding.edLastName.text.toString()
                val age = binding.edAge.text.toString()
                val email = binding.edEmail.text.toString()


                val user = hashMapOf(
                    "first" to firstname,
                    "last" to lastname,
                    "age" to age,
                    "email" to email
                )


                if (firstname.isNotEmpty() && lastname.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty()) {

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                            binding.userTextView.text = "User added successfully"
                            binding.userTextView.setTextColor(Color.GREEN)
                            activeUser++
                            binding.activeUsers.text = activeUser.toString()
                        }.addOnFailureListener {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Please, Fill all Fields!", Toast.LENGTH_SHORT).show()
                }
            }


            binding.updBtn.setOnClickListener {
                val intent = Intent(this, Update::class.java)
                startActivity(intent)
            }

            binding.delUsrBtn.setOnClickListener {
                val int1 = Intent(this, Delete::class.java)
                startActivity(int1)
                deletedUser++
                activeUser--
                binding.deletedUsers.text = deletedUser.toString()
                binding.activeUsers.text = activeUser.toString()
            }
        }

    }
