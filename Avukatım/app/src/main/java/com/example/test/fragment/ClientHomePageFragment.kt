package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.adapter.LawyerListAdapter
import com.example.test.databinding.FragmentClientHomePageBinding
import com.example.test.viewModel.HomePageViewModel


class ClientHomePageFragment : Fragment() {
    private lateinit var design : FragmentClientHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var lawyerListAdapter: LawyerListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_client_home_page, container, false)
        design.fragmetHomePage = this

        homePageViewModel.lawyerInfoList.observe(viewLifecycleOwner,{
            lawyerListAdapter = LawyerListAdapter(requireContext(),it,homePageViewModel)
            design.lawyerListAdapter = lawyerListAdapter
            Log.e("İD",it[0].id)
            for(a in it[0].lawyerProfession) {
                Log.e("Uzmanlık",a)
            }


        })

        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: HomePageViewModel by viewModels()
        homePageViewModel = tempViewModel
    }


}