package com.example.test.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.adapter.LawyerListAdapter
import com.example.test.databinding.FragmentClientHomePageBinding
import com.example.test.entity.LawyerInfo
import com.example.test.entity.LawyerInfoResult
import com.example.test.entity.MeetingDataClass
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.HomePageViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.test.retrofit.APIUtils


class ClientHomePageFragment : Fragment() {
    private lateinit var design : FragmentClientHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var lawyerListAdapter: LawyerListAdapter
    private lateinit var lawyerMeetingListViewModel : LawyerMeetingListViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: Adapter
    private lateinit var myLawyerDao : MyLawyerDaoInterface
    private var defaultFilterSearch = "name"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_client_home_page, container, false)
        design.fragmetHomePage = this
        auth = Firebase.auth


        getList("",defaultFilterSearch)

        design.txtProfF.setOnClickListener {
            defaultFilterSearch = "prodf"
            design.searchViewFood.setQuery("",true)
        }

        design.txtNameF.setOnClickListener {
            defaultFilterSearch = "name"
            design.searchViewFood.setQuery("",true)
        }



        design.searchViewFood.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
             return false

            }

            override fun onQueryTextChange(p0: String?): Boolean {

                getList(p0!!, defaultFilterSearch)

                return true
            }

        })



        design.swipeRefreshLayout.setOnRefreshListener {

          getList("",defaultFilterSearch)

            Handler().postDelayed(Runnable {
                design.swipeRefreshLayout.isRefreshing = false
            }, 1000)

        }











        return design.root
    }



    fun getDataLawyerInfo() {

        homePageViewModel.lawyerInfoList.observe(viewLifecycleOwner,{

            lawyerListAdapter = LawyerListAdapter(requireContext(),it,homePageViewModel)

            design.lawyerListAdapter = lawyerListAdapter
        })

    }




    fun getList(search:String,defaultSF:String) {
myLawyerDao = APIUtils.getMyLawyerDaoInterface()
        myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
            override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                val liste = response.body().lawyerInfoList

                if (search == "") {
                    lawyerListAdapter = LawyerListAdapter(requireContext(),liste,homePageViewModel)

                    design.lawyerListAdapter = lawyerListAdapter

                }else{

                    if (defaultSF == "name") {
                        val filterList : List<LawyerInfo> = liste.filter { it.lawyerNameSurname.toLowerCase().contains(search.toLowerCase()) }
                        lawyerListAdapter = LawyerListAdapter(requireContext(),filterList,homePageViewModel)

                        design.lawyerListAdapter = lawyerListAdapter
                    }else{
                        Log.e("Prof aranacak","${search}")


                        val filterListA : List<LawyerInfo> = liste.filter { it.lawyerProfession[0].toLowerCase().contains(search.toLowerCase()) }
                       Log.e("A","${filterListA.size}")

                        lawyerListAdapter = LawyerListAdapter(requireContext(),filterListA,homePageViewModel)

                        design.lawyerListAdapter = lawyerListAdapter

                        if (filterListA.size == 0) {
                            val filterListB : List<LawyerInfo> = liste.filter { it.lawyerProfession[1].toLowerCase().contains(search.toLowerCase()) }
                            Log.e("B","${filterListB.size}")

                            lawyerListAdapter = LawyerListAdapter(requireContext(),filterListB,homePageViewModel)

                            design.lawyerListAdapter = lawyerListAdapter
                            if (filterListB.size == 0){
                                val filterListC : List<LawyerInfo> = liste.filter { it.lawyerProfession[2].toLowerCase().contains(search.toLowerCase()) }
                                Log.e("C","${filterListC.size}")

                                lawyerListAdapter = LawyerListAdapter(requireContext(),filterListC,homePageViewModel)

                                design.lawyerListAdapter = lawyerListAdapter

                            }

                        }

                    }



                }








            }

            override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

            }

        })


    }


    override fun onResume() {
        super.onResume()
        getDataLawyerInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: HomePageViewModel by viewModels()
        homePageViewModel = tempViewModel





        val a : LawyerMeetingListViewModel by viewModels()
        lawyerMeetingListViewModel = a



    }


}