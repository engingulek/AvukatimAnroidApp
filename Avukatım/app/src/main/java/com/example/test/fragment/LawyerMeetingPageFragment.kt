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
import com.example.test.adapter.LawyerMeetingListAdapter
import com.example.test.databinding.FragmentLawyerMeetingPageBinding
import com.example.test.viewModel.HomePageViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.function.LongFunction


class LawyerMeetingPageFragment : Fragment() {
    private lateinit var design : FragmentLawyerMeetingPageBinding
    private lateinit var lawyerMeetingListViewModel: LawyerMeetingListViewModel
    private lateinit var lawyerMeetinfListAdapter : LawyerMeetingListAdapter
    private lateinit var auth: FirebaseAuth




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_meeting_page, container, false)
        design.meetingPageFragment = this

        lawyerMeetingListViewModel.meetingList.observe(viewLifecycleOwner,{

auth =  Firebase.auth
           val ita = it.filter { it.lawyerAuthUserId == auth.currentUser?.uid.toString() }
            lawyerMeetinfListAdapter = LawyerMeetingListAdapter(requireContext(),ita,lawyerMeetingListViewModel)
            design.lawyerMeetingListAdapter = lawyerMeetinfListAdapter
        })





        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : LawyerMeetingListViewModel by viewModels()
        lawyerMeetingListViewModel = tempViewModel
    }


}