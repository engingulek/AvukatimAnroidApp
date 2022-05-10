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


class ClientTabLayoutFragment : Fragment() {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()
    private lateinit var design : FragmentClientTabLayoutBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_tab_layout, container, false)

        design.fragmentClientTabLayout = this


        val adapter = MyViewPageAdapter(this)
        design.viewPager.adapter = adapter

      if (fragmentTitleList.isEmpty()) {
          fragmentList.add(ClientHomePageFragment())
          fragmentTitleList.add("Ana Sayfa")
          fragmentList.add(ClientAuthPageFragment())
         fragmentList.add(ChatListFragment())
          fragmentList.add(ClientMeetingList())

          if (fragmentTitleList.size == 1) {
              fragmentTitleList.add("Profil")
              fragmentTitleList.add("Mesaj")
              fragmentTitleList.add("TopliS")


          }
      }




// Basılan tablayouta ait fragmentin getirilmesi
        TabLayoutMediator(design.clientTabLayout,design.viewPager) { tab,position ->
            tab.setText(fragmentTitleList[position])

        }.attach()





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


}