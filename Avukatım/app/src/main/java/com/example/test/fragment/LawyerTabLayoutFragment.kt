package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.test.databinding.FragmentLawyerTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.example.test.R




class LawyerTabLayoutFragment : Fragment() {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()
    private lateinit var desing : FragmentLawyerTabLayoutBinding
    private val tabIcons = intArrayOf(
        R.drawable.selected_home_icon,
        R.drawable.meeting_icon,


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_tab_layout, container, false)

        desing.fragmentLawyerTabLayout = this
        val adapter = MyViewPageAdapter(this)
        desing.viewPager.adapter = adapter

        if (fragmentTitleList.isEmpty()) {

            fragmentList.add(LawyerHomePageFragment())
            fragmentTitleList.add("")
            fragmentList.add(LawyerChatListFragment())

            if(fragmentTitleList.size == 1) {
                fragmentTitleList.add("")

            }




        }


        TabLayoutMediator(desing.lawyerTabLayout,desing.viewPager) {tab,position ->
            tab.setText(fragmentTitleList[position])
        }.attach()

        setupTabIcons()
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
    fun setupTabIcons() {
        desing.lawyerTabLayout.getTabAt(0)!!.setIcon(tabIcons[0])
        desing.lawyerTabLayout.getTabAt(1)!!.setIcon(tabIcons[1])
    }


}