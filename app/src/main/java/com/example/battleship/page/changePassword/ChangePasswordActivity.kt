package com.example.battleship.page.changePassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.battleship.R
import com.example.battleship.const.Mode
import com.example.battleship.databinding.ActivityChangePasswordBinding
import com.example.battleship.utils.NavigationUtils
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {
    private val changePasswordViewModel:ChangePasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        binding.backIcon.setOnClickListener{
            finish()
        }
        binding.submitButton.setOnClickListener{
            val old = binding.oldPasswordEditText.text.toString()
            val new = binding.newPasswordEditText.text.toString()
            changePasswordViewModel.changePassword(old,new)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                changePasswordViewModel.updateState.collect{ state->
                    when (state) {
                        1 -> showSuccessDialog()
                        2 -> showErrorDialog("旧密码错误")
                        3 -> showErrorDialog("密码修改失败")
                    }
                }
            }
        }
    }
    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("成功")
            .setMessage("密码修改成功")
            .setPositiveButton("确定") { dialog, _ ->
                dialog.dismiss()
                finish() // 可选：关闭当前活动
            }
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("错误")
            .setMessage(message)
            .setPositiveButton("确定") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}