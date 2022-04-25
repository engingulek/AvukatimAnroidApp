package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.adapter.LawyerMeetingListAdapter
import com.example.test.databinding.FragmentLawyerHomePageBinding
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LawyerHomePageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var design : FragmentLawyerHomePageBinding
    private lateinit var lawyerMeetingListViewModel: LawyerMeetingListViewModel
    private lateinit var lawyerMeetinfListAdapter : LawyerMeetingListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_home_page, container, false)
        design.fragmentLawyerHomePage = this
        design.authUser = auth


        lawyerMeetingListViewModel.meetingList.observe(viewLifecycleOwner,{

            auth =  Firebase.auth
            val ita = it.filter { it.lawyerAuthUserId == auth.currentUser?.uid.toString() }


            lawyerMeetinfListAdapter = LawyerMeetingListAdapter(requireContext(),ita,lawyerMeetingListViewModel)
            for (a in ita) {
                Log.e("İfta","${a.lawyerAuthUserId}")
            }
            design.lawyerMeetingListAdapter = lawyerMeetinfListAdapter

            if (ita.size > 0) {
                design.nullLawyerAdvert.visibility = View.GONE
                design.getMeetingConstLa.visibility = View.VISIBLE

            }

            else {
                design.nullLawyerAdvert.visibility = View.VISIBLE
                design.getMeetingConstLa.visibility = View.GONE


            }

        })

        design.bttntoCreateAdvert.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toLawyerCreateAdvert)
        }


        design.lawyerUserImagView.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toLawyerAuth)
        }

        return design.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : LawyerMeetingListViewModel by viewModels()
        lawyerMeetingListViewModel = tempViewModel
    }

}