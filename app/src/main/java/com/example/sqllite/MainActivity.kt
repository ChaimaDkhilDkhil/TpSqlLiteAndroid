package com.example.sqllite
import EtudiantDBHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPrenom: EditText
    private lateinit var editTextTel: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnValider: Button
    private lateinit var btnAnnuler: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextPrenom = findViewById(R.id.editTextPrenom)
        editTextTel = findViewById(R.id.editTextTel)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextLogin = findViewById(R.id.editTextLogin)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)

        btnValider.setOnClickListener {
            Log.d("com.example.sqlite.MainActivity", "Button Validate clicked")

            if (areInputFieldsEmpty()) {
                showEmptyFieldsDialog()
            } else {
                try {
                    insertStudent()
                    redirectToStudentList()
                } catch (e: Exception) {
                    Log.e("com.example.sqlite.MainActivity", "Error during insertion: ${e.message}")
                }
            }
        }

        btnAnnuler.setOnClickListener {
            showCancellationDialog()
        }
    }

    private fun areInputFieldsEmpty(): Boolean {
        return editTextName.text.isEmpty() || editTextPrenom.text.isEmpty() || editTextTel.text.isEmpty() ||
                editTextEmail.text.isEmpty() || editTextLogin.text.isEmpty() || editTextPassword.text.isEmpty()
    }

    private fun showEmptyFieldsDialog() {
        AlertDialog.Builder(this)
            .setMessage("All fields must be filled in")
            .setTitle("Attention")
            .setPositiveButton("OK") { dialogInterface, _ ->dialogInterface.dismiss()
            }
            .show()
    }

    private fun insertStudent() {
        val values = ContentValues().apply {
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, editTextName.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, editTextPrenom.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PHONE, editTextTel.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL, editTextEmail.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN, editTextLogin.text.toString())
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP, editTextPassword.text.toString())
        }

        val dbHelper = EtudiantDBHelper(applicationContext)
        val db = dbHelper.writableDatabase

        try {
            db.insert(EtudiantBC.EtudiantEntry.TABLE_NAME, null, values)
        } finally {
            db.close()
            dbHelper.close()
        }
    }

    private fun redirectToStudentList() {
        val intent = Intent(this, ListEtudiant::class.java)
        startActivity(intent)
    }

    private fun showCancellationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Do you really want to cancel?")
            .setTitle("Confirmation")
            .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss()
            }
            .setPositiveButton("Yes") { _, _ -> clearInputFields()
            }
            .show()
    }

    private fun clearInputFields() {
        editTextName.text.clear()
        editTextPrenom.text.clear()
        editTextTel.text.clear()
        editTextEmail.text.clear()
        editTextLogin.text.clear()
        editTextPassword.text.clear()
    }
}
