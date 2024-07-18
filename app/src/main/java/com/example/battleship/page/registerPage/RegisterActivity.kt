package com.example.battleship.page.registerPage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.battleship.MainActivity
import com.example.battleship.R
import com.example.battleship.const.Mode
import com.example.battleship.databinding.ActivityRegisterBinding
import com.example.battleship.network.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val registerViewModel:RegisterViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            if (Mode.isGestureNav)
            {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            }
            else{
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            }
            insets
        }
        val intent = Intent(this,MainActivity::class.java)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.registerState.collect{state->
                    if (state == 1){
                        startActivity(intent)
                    }else if (state == 2){
                        Toast.makeText(this@RegisterActivity,registerViewModel.errorMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.loginLink.setOnClickListener {
            finish()
        }
        binding.registerButton.setOnClickListener {
            val userName = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassWord = binding.confirmPasswordEditText.text.toString()
            if (userName == ""||email == ""||password == ""||confirmPassWord == ""){
                Toast.makeText(this,"请正确填写信息",Toast.LENGTH_SHORT).show()
            }else if (password != confirmPassWord){
                Toast.makeText(this,"两次输入密码不一致",Toast.LENGTH_SHORT).show()
            }else{
                registerViewModel.register(User(userName,password,email))
            }
        }
    }
}