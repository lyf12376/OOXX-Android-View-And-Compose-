package com.example.battleship

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.battleship.databinding.ActivityMainBinding
import com.example.battleship.adapters.ViewPagerAdapter
import com.example.battleship.const.Mode
import com.example.battleship.utils.InitialOfflineGame
import com.example.battleship.utils.NavigationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT) // light causes internally enforce the navigation bar to be fully transparent
        )
        val binding = ActivityMainBinding.inflate(layoutInflater)
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
        lifecycleScope.launch {
            if (InitialOfflineGame.isRoomDatabaseExist(this@MainActivity)) {
                InitialOfflineGame.initialOfflineGame(this@MainActivity)
            }
        }
        var isHomeSelected = false
        var isMineSelected = false
        val viewPager = binding.mainViewPager
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isNestedScrollingEnabled = false
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.bottomNavigation.selectedItemId = R.id.navigation_home
                        isHomeSelected = true
                        isMineSelected = false
                    }
                    1 -> {
                        binding.bottomNavigation.selectedItemId = R.id.navigation_me
                        isHomeSelected = false
                        isMineSelected = true
                    }
                }
            }
        })
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (isHomeSelected) {
                        // 如果当前已经选中 Home 按钮，则不处理
                        false
                    }
                    // 处理 Home 按钮点击事件
                    else {
                        Log.d("MainActivity", "Home clicked")
                        viewPager.setCurrentItem(0, true)
                        isHomeSelected = true
                        isMineSelected = false
                        true
                    }
                }
                R.id.navigation_me -> {
                    if (isMineSelected) {
                        // 如果当前已经选中 Mine 按钮，则不处理
                        false
                    }
                    else{
                        Log.d("MainActivity", "Mine clicked")
                        viewPager.setCurrentItem(1, true)
                        isMineSelected = true
                        isHomeSelected = false
                        true
                    }
                }
                else -> false
            }
        }


    }

}