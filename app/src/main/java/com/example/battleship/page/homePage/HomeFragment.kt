package com.example.battleship.page.homePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.battleship.R
import com.example.battleship.adapters.LevelPagerAdapter
import com.example.battleship.const.User
import com.example.battleship.databinding.FragmentHomeBinding
import com.example.battleship.page.gamePage.GameActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用生成的绑定类的 `inflate` 方法
        val binding: FragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        binding.username.text = User.userName
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.level.collect { data ->
                    binding.whichLevel.text = "第${data+1}关"
                    binding.selectLevel.currentItem = data
                }
            }
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewBinding = FragmentHomeBinding.bind(view)
        val viewpager = viewBinding.selectLevel
        val iconList = listOf(R.drawable.first_level,R.drawable.second_level,R.drawable.third_level)
        viewpager.adapter = LevelPagerAdapter(iconList)
        viewBinding.lastLevel.setOnClickListener{
            if (viewModel.level.value > 0) {
                viewModel.setLevel(viewModel.level.value - 1)
            }
        }
        viewBinding.nextLevel.setOnClickListener {
            if (viewModel.level.value<iconList.size-1){
                viewModel.setLevel(viewModel.level.value + 1)
            }
        }

        viewBinding.play.setOnClickListener {
            val intent = Intent(activity, GameActivity::class.java)
            intent.putExtra("level",viewModel.level.value)
            startActivity(intent)
        }


    }
}


