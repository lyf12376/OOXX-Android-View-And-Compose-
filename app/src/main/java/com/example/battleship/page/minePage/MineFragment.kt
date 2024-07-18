package com.example.battleship.page.minePage

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.battleship.R
import com.example.battleship.adapters.MineOptionAdapter
import com.example.battleship.bean.MineOption
import com.example.battleship.const.User
import com.example.battleship.databinding.FragmentMineBinding
import com.example.battleship.page.changePassword.ChangePasswordActivity
import com.example.battleship.page.gameHistoryPage.GameHistoryActivity

class MineFragment : Fragment() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val mineViewModel: MineViewModel by viewModels()
    private lateinit var binding: FragmentMineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.optionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.userName.text = User.userName

        val mineOptionAdapter = MineOptionAdapter(
            listOf(
                MineOption(R.drawable.change_password, "修改密码") {
                    val intent = Intent(activity, ChangePasswordActivity::class.java)
                    startActivity(intent)
                },
                MineOption(R.drawable.game_history,"游戏历史"){
                    val intent = Intent(activity,GameHistoryActivity::class.java)
                    startActivity(intent)
                }
            )
        )
        binding.optionsRecyclerView.adapter = mineOptionAdapter
    }
}


