package com.example.battleship.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.battleship.page.homePage.HomeFragment
import com.example.battleship.page.minePage.MineFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // 返回ViewPager中的Fragment数量
    override fun getItemCount(): Int {
        return 2 // 假设有3个Fragment
    }

    // 根据位置返回对应的Fragment实例
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MineFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
