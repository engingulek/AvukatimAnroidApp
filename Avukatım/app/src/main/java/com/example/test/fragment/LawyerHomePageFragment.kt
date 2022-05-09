package com.example.test.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.MainActivity
import com.example.test.R
import com.example.test.adapter.LawyerListAdapter
import com.example.test.adapter.LawyerMeetingListAdapter
import com.example.test.databinding.FragmentLawyerHomePageBinding
import com.example.test.entity.LawyerInfoResult
import com.example.test.entity.MeetingDataClassResult
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.HomePageViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.grpc.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.core.content.ContextCompat.getSystemService

import android.os.Build
import androidx.core.content.ContextCompat


class LawyerHomePageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var design : FragmentLawyerHomePageBinding
    private lateinit var lawyerMeetingListViewModel: LawyerMeetingListViewModel
    private lateinit var lawyerMeetinfListAdapter : LawyerMeetingListAdapter
    private lateinit var homePageViewModel : HomePageViewModel
    private lateinit var myLawyerDao : MyLawyerDaoInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_home_page, container, false)
        design.fragmentLawyerHomePage = this
        design.authUser = auth

        design.testbttn.setOnClickListener {







        }


        getData()
        design.swipeRefreshLayoutMeetingList.setOnRefreshListener {
            getData()

            Handler().postDelayed(Runnable {
                design.swipeRefreshLayoutMeetingList.isRefreshing = false
            }, 1000)

        }
        design.bttntoCreateAdvert.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toLawyerCreateAdvert)
        }


        design.lawyerUserImagView.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toLawyerAuth)
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
                lawyerMeetinfListAdapter = LawyerMeetingListAdapter(requireContext(),list,lawyerMeetingListViewModel)
                design.lawyerMeetingListAdapter = lawyerMeetinfListAdapter






            }

            override fun onFailure(call: Call<MeetingDataClassResult>, t: Throwable) {

            }

        })

        myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
            override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                val liste = response.body().lawyerInfoList
                val ita = liste.filter { a -> a.authUserId == auth.currentUser?.uid }
                if (ita.size > 0) {
                    design.nullLawyerAdvert.visibility = View.GONE
                    design.getMeetingConstLa.visibility = View.VISIBLE

                }

                else {
                    design.nullLawyerAdvert.visibility = View.VISIBLE
                    design.getMeetingConstLa.visibility = View.GONE


                }










            }

            override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

            }

        })
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : LawyerMeetingListViewModel by viewModels()
        lawyerMeetingListViewModel = tempViewModel


        val tempViewModell : HomePageViewModel by viewModels()
        homePageViewModel = tempViewModell





    }

}