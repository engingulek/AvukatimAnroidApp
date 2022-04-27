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
import com.example.test.entity.MeetingDataClass
import com.example.test.viewModel.HomePageViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ClientHomePageFragment : Fragment() {
    private lateinit var design : FragmentClientHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var lawyerListAdapter: LawyerListAdapter
    private lateinit var lawyerMeetingListViewModel : LawyerMeetingListViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_client_home_page, container, false)
        design.fragmetHomePage = this
        auth = Firebase.auth





        homePageViewModel.lawyerInfoList.observe(viewLifecycleOwner,{
            val lawyerList = it
            lawyerMeetingListViewModel.meetingList.observe(viewLifecycleOwner,{


                if (it.size > 0) {
                    for ( a in it) {
                        val ita = lawyerList.filter { lawyerList -> lawyerList.authUserId != a.lawyerAuthUserId }
                        lawyerListAdapter = LawyerListAdapter(requireContext(),ita,homePageViewModel)
                        design.lawyerListAdapter = lawyerListAdapter

                    }



                }

                else {
                    lawyerListAdapter = LawyerListAdapter(requireContext(),lawyerList,homePageViewModel)
                    design.lawyerListAdapter = lawyerListAdapter

                }




            })














            //  val ita = it.filter { it.authUserId ==  }


        })

        return design.root
    }

    override fun onResume() {
        super.onResume()
        homePageViewModel.lawyerInfoList.observe(viewLifecycleOwner,{
            val lawyerList = it
            lawyerMeetingListViewModel.meetingList.observe(viewLifecycleOwner,{





                if (it.size > 0) {
                    for ( a in it) {
                        val ita = lawyerList.filter { lawyerList -> lawyerList.authUserId != a.lawyerAuthUserId }
                        lawyerListAdapter = LawyerListAdapter(requireContext(),ita,homePageViewModel)
                        design.lawyerListAdapter = lawyerListAdapter

                    }



                }

                else {
                    lawyerListAdapter = LawyerListAdapter(requireContext(),lawyerList,homePageViewModel)
                    design.lawyerListAdapter = lawyerListAdapter

                }




            })














            //  val ita = it.filter { it.authUserId ==  }


        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: HomePageViewModel by viewModels()
        homePageViewModel = tempViewModel




        val a : LawyerMeetingListViewModel by viewModels()
        lawyerMeetingListViewModel = a
    }


}