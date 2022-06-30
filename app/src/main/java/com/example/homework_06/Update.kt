package com.example.homework_06

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_06.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class Update : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()

        binding.updateBtn.setOnClickListener {
            val email = binding.edExEmail.text.toString()
            val newEmail = binding.editNewEmail.text.toString()
            binding.edExEmail.setText("")
            binding.editNewEmail.setText("")
            UpdateData(email, newEmail)

        }

    }

    private fun UpdateData(email: String, newEmail: String) {

        val userDetail = hashMapOf<String, Any>()
        userDetail.put("email", newEmail)
        db.collection("users")
            .whereEqualTo("email", email)
            .get().addOnCompleteListener {
                if (it.isSuccessful && !it.getResult().isEmpty) {
                    val documentSnapshot: DocumentSnapshot = it.getResult().getDocuments().get(0)
                    val documentId = documentSnapshot.getId()
                    db.collection("users")
                        .document(documentId)
                        .update(userDetail as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successfuly updated", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show()
            }
    }
}
