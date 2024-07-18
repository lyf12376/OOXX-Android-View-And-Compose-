package com.example.battleship.page.loginPage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.battleship.MainActivity
import com.example.battleship.adapters.SavedUserAdapter
import com.example.battleship.const.Mode
import com.example.battleship.databinding.ActivityLoginBinding
import com.example.battleship.localDatabase.savedUser.SavedUser
import com.example.battleship.page.registerPage.RegisterActivity
import com.example.battleship.utils.NavigationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT) // light causes internally enforce the navigation bar to be fully transparent
        )
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            if (NavigationUtils.isGestureNav(insets)) {
                Mode.isGestureNav = true
                Mode.paddingValues = this.paddingValues(systemBars.left, systemBars.right, systemBars.top, 0)
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            } else {
                Mode.isThreeNav = true
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            }
            insets
        }


        // 观察ViewModel中的savedUserList
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                loginViewModel.savedUserList.first().let { users ->
                    Log.d("TAG", "onCreate: $users")
                    if (users.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            binding.checkBoxRememberPassword.isChecked = true
                            binding.emailEditText.text = Editable.Factory.getInstance().newEditable(users[0].account)
                            binding.passwordEditText.text = Editable.Factory.getInstance().newEditable(users[0].password)
                        }
                    }
                }
            }
        }


        // 观察ViewModel中的其他状态
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.isLoginSuccess.collect{isSuccess->
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    if (isSuccess){
                        startActivity(intent)
                    }
                }
            }
        }


        lifecycleScope.launch {
            loginViewModel.isLoginFailed.collect { isFailed ->
                if (isFailed) {
                    Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 示例：调用login方法
        binding.loginButton.setOnClickListener {
            val account = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            Log.d("TAG", "onCreate: $account $password")
            if (binding.checkBoxRememberPassword.isChecked){
                loginViewModel.deleteAll()
                loginViewModel.rememberAccount(account,password)
            }else{
                loginViewModel.unRememberAccount(account)
            }
            loginViewModel.login(account,password)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}

fun Context.paddingValues(start:Int,end:Int,top:Int,bottom:Int):PaddingValues{
    val density = this.resources.displayMetrics.density
    return PaddingValues(start = (start/(density.toInt())).dp,end = (end/(density.toInt())).dp,top = (top/(density.toInt())).dp, bottom = (bottom/(density.toInt())).dp)
}
