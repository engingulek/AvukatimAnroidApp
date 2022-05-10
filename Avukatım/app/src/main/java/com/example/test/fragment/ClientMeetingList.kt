package com.example.test.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.adapter.ClientMeetingListAdapter
import com.example.test.adapter.LawyerMeetingListAdapter
import com.example.test.databinding.FragmentClientMeetingListBinding
import com.example.test.entity.LawyerInfoResult
import com.example.test.entity.MeetingDataClassResult
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.ClientMeetingListViewModel
import com.example.test.viewModel.HomePageViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientMeetingList : Fragment() {

    private lateinit var  design : FragmentClientMeetingListBinding
    private lateinit var myLawyerDao : MyLawyerDaoInterface
    private lateinit var auth: FirebaseAuth
    private lateinit var clientMeetingListViewModel: ClientMeetingListViewModel
    private lateinit var clientMeetingListAdapter: ClientMeetingListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_meeting_list, container, false)

        var a = ClientTabLayoutFragment()

        design.fragmentLawyerHomePage = this
        auth = Firebase.auth
        getData()
        design.swipeRefreshLayoutMeetingList.setOnRefreshListener {
            getData()

            Handler().postDelayed(Runnable {
                design.swipeRefreshLayoutMeetingList.isRefreshing = false
            }, 1000)

        }








        return design.root
    }

    override fun onResume() {
        super.onResume()
        getData()


    }

    fun getData() {
        myLawyerDao = APIUtils.getMyLawyerDaoInterface()
        myLawyerDao.getAllMeetingList().enqueue(object : Callback<MeetingDataClassResult> {
            override fun onResponse(
                call: Call<MeetingDataClassResult>,
                response: Response<MeetingDataClassResult>
            ) {
                val list = response.body().meetingsList

               clientMeetingListAdapter = ClientMeetingListAdapter(requireContext(),list,clientMeetingListViewModel)
                design.clientMeetingListAdapter = clientMeetingListAdapter






            }

            override fun onFailure(call: Call<MeetingDataClassResult>, t: Throwable) {

            }

        })


    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : ClientMeetingListViewModel by viewModels()
        clientMeetingListViewModel = tempViewModel








    }


    }


