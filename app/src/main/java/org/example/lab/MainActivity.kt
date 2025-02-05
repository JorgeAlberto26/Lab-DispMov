package org.example.lab

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnCreateUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // Usa el layout principal (activity_main.xml)

        // Inflar el layout de creación de usuario (login.xml) dentro del contenedor
        val inflater = LayoutInflater.from(this)
        val loginView = inflater.inflate(R.layout.login, null)
        findViewById<FrameLayout>(R.id.container).addView(loginView)

        // Inicializar vistas
        etName = loginView.findViewById(R.id.etName)
        etEmail = loginView.findViewById(R.id.etEmail)
        etPassword = loginView.findViewById(R.id.etPassword)
        btnCreateUser = loginView.findViewById(R.id.btnCreateUser)

        // Configurar el clic del botón
        btnCreateUser.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí puedes agregar la lógica para crear el usuario, como enviar los datos a un servidor o guardarlos en una base de datos local.
        // Por ahora, solo mostraremos un mensaje de éxito.
        Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()

        // Limpiar los campos después de crear el usuario
        etName.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()
    }
}