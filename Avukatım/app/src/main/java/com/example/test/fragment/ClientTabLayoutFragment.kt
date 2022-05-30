package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.R
import com.example.test.databinding.FragmentClientTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayout


import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class ClientTabLayoutFragment : Fragment() {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()
    private lateinit var design : FragmentClientTabLayoutBinding
    private val tabIcons = intArrayOf(

        R.drawable.home,
        R.drawable.meet,
        R.drawable.chat





        )


    private val selecttabIcons = intArrayOf(
        R.drawable.selected_home,
        R.drawable.selected_meet,
        R.drawable.select_chat




    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_tab_layout, container, false)

        design.fragmentClientTabLayout = this
        design.clientTabLayout.getTabAt(0)?.setIcon(selecttabIcons[0])

        val adapter = MyViewPageAdapter(this)
        design.viewPager.adapter = adapter

      if (fragmentTitleList.isEmpty()) {
          fragmentList.add(ClientHomePageFragment())
          fragmentTitleList.add("")
          fragmentList.add(ClientMeetingList())
         fragmentList.add(ChatListFragment())
          if (fragmentTitleList.size == 1) {
              fragmentTitleList.add("")
              fragmentTitleList.add("")
          }
      }
// Basılan tablayouta ait fragmentin getirilmesi
        TabLayoutMediator(design.clientTabLayout,design.viewPager) { tab,position ->
            tab.setText(fragmentTitleList[position])
        }.attach()
        setupTabIcons()






        design.clientTabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {


                if (design.clientTabLayout.getTabAt(0)?.isSelected == true) {
                    design.clientTabLayout.getTabAt(0)?.setIcon(selecttabIcons[0])
                    design.clientTabLayout.getTabAt(1)?.setIcon(tabIcons[1])
                    design.clientTabLayout.getTabAt(2)?.setIcon(tabIcons[2])
                }


                if (design.clientTabLayout.getTabAt(1)?.isSelected == true) {
                    design.clientTabLayout.getTabAt(1)?.setIcon(selecttabIcons[1])
                    design.clientTabLayout.getTabAt(0)?.setIcon(tabIcons[0])
                    design.clientTabLayout.getTabAt(2)?.setIcon(tabIcons[2])
                }

                if (design.clientTabLayout.getTabAt(2)?.isSelected == true) {
                    design.clientTabLayout.getTabAt(0)?.setIcon(tabIcons[0])
                    design.clientTabLayout.getTabAt(1)?.setIcon(tabIcons[1])
                    design.clientTabLayout.getTabAt(2)?.setIcon(selecttabIcons[2])
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })




        return design.root
    }




    // fragment listesi kadar yer oluşturulması
    inner class MyViewPageAdapter(fragmentClientTabLayoutFragment: ClientTabLayoutFragment)
        : FragmentStateAdapter(fragmentClientTabLayoutFragment) {
        override fun getItemCount(): Int {
            return fragmentList.size

        }

        // fragmentlerin oluşturulması
        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]

        }

    }

    fun setupTabIcons() {
        design.clientTabLayout.getTabAt(0)!!.setIcon(selecttabIcons[0])
        design.clientTabLayout.getTabAt(1)!!.setIcon(tabIcons[1])
        design.clientTabLayout.getTabAt(2)!!.setIcon(tabIcons[2])

    }




}