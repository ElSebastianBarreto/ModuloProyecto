package com.example.moduloproyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.annotation.Nonnull

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        setup()
    }

    private fun setup() {
        title = "Autenticación"
        val btnRegister: Button = findViewById(R.id.btnRegistrer)

        // Inicializa EditText
        mEditTextEmail = findViewById(R.id.editTextEmail)
        mEditTextPassword = findViewById(R.id.editTextPassword)

        btnRegister.setOnClickListener {
            if (mEditTextEmail.text.isNotEmpty() && mEditTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    mEditTextEmail.text.toString(),
                    mEditTextPassword.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // Redirige a ProfileActivity
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        finish() // Opcional: Finaliza MainActivity para que no vuelva atrás
                    } else {
                        showAlert()
                    }
                }
            } else {
                // Puedes agregar aquí un mensaje si los campos están vacíos
                showAlert()
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error al registrar")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}
