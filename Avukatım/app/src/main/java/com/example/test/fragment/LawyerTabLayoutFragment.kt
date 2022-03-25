package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.R
import com.example.test.databinding.FragmentLawyerTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class LawyerTabLayoutFragment : Fragment() {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()
    private lateinit var desing : FragmentLawyerTabLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_tab_layout, container, false)
        fragmentList.add(LawyerHomePageFragment())
        desing.fragmentLawyerTabLayout = this

        val adapter = MyViewPageAdapter(this)
        desing.viewPager.adapter = adapter
        fragmentTitleList.add("Ana Sayfa")
        TabLayoutMediator(desing.lawyerTabLayout,desing.viewPager) {tab,position ->
            tab.setText(fragmentTitleList[position])
        }.attach()
        return desing.root
    }

    inner class MyViewPageAdapter(fragmentTabLayoutFragment: LawyerTabLayoutFragment)
        : FragmentStateAdapter(fragmentTabLayoutFragment) {
        override fun getItemCount(): Int {
            return fragmentList.size

        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]

        }

    }


}