package com.example.moduloproyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        setup()
    }
    private fun setup() {
        title = "Autenticación"
        emailLogin = findViewById(R.id.EmailLogin)
        passwordLogin = findViewById(R.id.PasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            if (emailLogin.text.isNotEmpty() && passwordLogin.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailLogin.text.toString(),
                        passwordLogin.text.toString()
                    ).addOnCompleteListener {
                    if(it.isSuccessful){
                        showExitologin()
                        val intent = Intent(this, Index::class.java)
                        startActivity(intent)
                    }
                        else{
                            showAlert()
                        }


                }
            }
        }
    }




    private fun showExitologin() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login exitoso")
        builder.setMessage("Bienvenido")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error al Login")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}