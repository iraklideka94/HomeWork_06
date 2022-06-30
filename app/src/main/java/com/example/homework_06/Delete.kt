package com.example.homework_06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework_06.databinding.ActivityDeleteBinding
import com.example.homework_06.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Delete : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()

        binding.deleteBtn.setOnClickListener {
            val email = binding.edDelete.text.toString()
            binding.edDelete.setText("")
            DeleteData(email)

        }



        }
    fun DeleteData(email: String) {
        db.collection("users")
            .whereEqualTo("email",email)
            .get().addOnCompleteListener {
                if (it.isSuccessful && !it.getResult().isEmpty){
                    val documentSnapshot: DocumentSnapshot = it.getResult().getDocuments().get(0)
                    var documentId = documentSnapshot.getId()
                    db.collection("users")
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successfuly Deleted", Toast.LENGTH_SHORT).show()
                        }
                }
            }}

    }

