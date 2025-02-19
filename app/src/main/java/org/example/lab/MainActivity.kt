package org.example.lab

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnCreateUser: Button
    private lateinit var etConfirmPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.main_activity)

        auth = FirebaseAuth.getInstance()

        // Inflar el layout de creación de usuario (login.xml) dentro del contenedor
        val inflater = LayoutInflater.from(this)
        val loginView = inflater.inflate(R.layout.login, null)
        findViewById<FrameLayout>(R.id.container).addView(loginView)

        // Inicializar vistas
        etName = loginView.findViewById(R.id.etName)
        etEmail = loginView.findViewById(R.id.etEmail)
        etPassword = loginView.findViewById(R.id.etPassword)
        btnCreateUser = loginView.findViewById(R.id.btnCreateUser)
        etConfirmPassword = loginView.findViewById(R.id.etConfirmPassword)

        // Configurar el clic del botón
        btnCreateUser.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Validar campos vacíos
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que las contraseñas coincidan
        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear usuario con Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    val user = auth.currentUser
                    sendEmailVerification(user) // Enviar correo de verificación
                } else {
                    // Error en el registro
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendEmailVerification(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de verificación enviado a ${user.email}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al enviar el correo de verificación", Toast.LENGTH_SHORT).show()
                }
            }
    }
}