package com.devmasterteam.tasks.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.databinding.ActivityRegisterBinding
import com.devmasterteam.tasks.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        // Eventos
        binding.buttonSave.setOnClickListener(this)
        binding.textBackLogin.setOnClickListener(this)

        //Observe
        observe()

        // Layout
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            handleSave()
        }
        if (v.id == R.id.text_back_login) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.user.observe(this) {
            if (it.status()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleSave() {
        val name = binding.editName.toString()
        val email = binding.editEmail.toString()
        val password = binding.editPassword.toString()

        viewModel.create(name, email, password)
    }
}