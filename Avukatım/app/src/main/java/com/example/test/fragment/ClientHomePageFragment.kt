package com.example.test.fragment

import android.graphics.Color
import android.graphics.Color.RED
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


        design.txtNameF.setBackgroundResource(R.drawable.selected_text_design)
        design.txtNameF.setTextColor(Color.WHITE)
        design.txtAll.setBackgroundResource(R.drawable.selected_text_design)
        design.txtAll.setTextColor(Color.WHITE)
        getList("",defaultFilterSearch)



        desingClickAction()





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


    fun desingClickAction() {
        design.txtAll.setOnClickListener {
            getList("",defaultFilterSearch)


            design.txtAll.setBackgroundResource(R.drawable.selected_text_design)
            design.txtAll.setTextColor(Color.WHITE)


            design.txtFWoman.setBackgroundResource(R.drawable.search_view_design)
            design.txtFWoman.setTextColor(Color.BLACK)

            design.textFMan.setBackgroundResource(R.drawable.search_view_design)
            design.textFMan.setTextColor(Color.BLACK)


            design.txtAgeU.setBackgroundResource(R.drawable.search_view_design)
            design.txtAgeU.setTextColor(Color.BLACK)

            design.txtAD.setBackgroundResource(R.drawable.search_view_design)
            design.txtAD.setTextColor(Color.BLACK)

        }


        design.txtFWoman.setOnClickListener {

            myLawyerDao = APIUtils.getMyLawyerDaoInterface()
            myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
                override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                    val liste = response.body().lawyerInfoList
                    val filterList : List<LawyerInfo> = liste.filter { it.lawyerGender.toLowerCase().contains("d".toLowerCase()) }
                    lawyerListAdapter = LawyerListAdapter(requireContext(),filterList,homePageViewModel)

                    design.lawyerListAdapter = lawyerListAdapter




                }

                override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

                }

            })





            design.txtFWoman.setBackgroundResource(R.drawable.selected_text_design)
            design.txtFWoman.setTextColor(Color.WHITE)


            design.txtAll.setBackgroundResource(R.drawable.search_view_design)
            design.txtAll.setTextColor(Color.BLACK)

            design.textFMan.setBackgroundResource(R.drawable.search_view_design)
            design.textFMan.setTextColor(Color.BLACK)


            design.txtAgeU.setBackgroundResource(R.drawable.search_view_design)
            design.txtAgeU.setTextColor(Color.BLACK)

            design.txtAD.setBackgroundResource(R.drawable.search_view_design)
            design.txtAD.setTextColor(Color.BLACK)

        }










        design.textFMan.setOnClickListener {
            myLawyerDao = APIUtils.getMyLawyerDaoInterface()
            myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
                override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                    val liste = response.body().lawyerInfoList
                    val filterList : List<LawyerInfo> = liste.filter { it.lawyerGender.toLowerCase().contains("e".toLowerCase()) }
                    lawyerListAdapter = LawyerListAdapter(requireContext(),filterList,homePageViewModel)

                    design.lawyerListAdapter = lawyerListAdapter




                }

                override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

                }

            })
            design.textFMan.setBackgroundResource(R.drawable.selected_text_design)
            design.textFMan.setTextColor(Color.WHITE)

            design.txtAll.setBackgroundResource(R.drawable.search_view_design)
            design.txtAll.setTextColor(Color.BLACK)

            design.txtFWoman.setBackgroundResource(R.drawable.search_view_design)
            design.txtFWoman.setTextColor(Color.BLACK)


            design.txtAgeU.setBackgroundResource(R.drawable.search_view_design)
            design.txtAgeU.setTextColor(Color.BLACK)

            design.txtAD.setBackgroundResource(R.drawable.search_view_design)
            design.txtAD.setTextColor(Color.BLACK)

        }



        design.txtAgeU.setOnClickListener {

            myLawyerDao = APIUtils.getMyLawyerDaoInterface()
            myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
                override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                    val liste = response.body().lawyerInfoList
                    val filterList : List<LawyerInfo> = liste.sortedBy { it.lawyerAge.toInt() }
                    lawyerListAdapter = LawyerListAdapter(requireContext(),filterList,homePageViewModel)

                    design.lawyerListAdapter = lawyerListAdapter




                }

                override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

                }

            })



            design.txtAgeU.setBackgroundResource(R.drawable.selected_text_design)
            design.txtAgeU.setTextColor(Color.WHITE)


            design.txtAll.setBackgroundResource(R.drawable.search_view_design)
            design.txtAll.setTextColor(Color.BLACK)

            design.txtFWoman.setBackgroundResource(R.drawable.search_view_design)
            design.txtFWoman.setTextColor(Color.BLACK)


            design.textFMan.setBackgroundResource(R.drawable.search_view_design)
            design.textFMan.setTextColor(Color.BLACK)

            design.txtAD.setBackgroundResource(R.drawable.search_view_design)
            design.txtAD.setTextColor(Color.BLACK)

        }


        design.txtAD.setOnClickListener {

            myLawyerDao = APIUtils.getMyLawyerDaoInterface()
            myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
                override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                    val liste = response.body().lawyerInfoList
                    val filterList : List<LawyerInfo> = liste.sortedByDescending { it.lawyerAge.toInt() }
                    lawyerListAdapter = LawyerListAdapter(requireContext(),filterList,homePageViewModel)

                    design.lawyerListAdapter = lawyerListAdapter




                }

                override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

                }

            })

            design.txtAD.setBackgroundResource(R.drawable.selected_text_design)
            design.txtAD.setTextColor(Color.WHITE)


            design.txtAll.setBackgroundResource(R.drawable.search_view_design)
            design.txtAll.setTextColor(Color.BLACK)

            design.txtFWoman.setBackgroundResource(R.drawable.search_view_design)
            design.txtFWoman.setTextColor(Color.BLACK)


            design.textFMan.setBackgroundResource(R.drawable.search_view_design)
            design.textFMan.setTextColor(Color.BLACK)


            design.txtAgeU.setBackgroundResource(R.drawable.search_view_design)
            design.txtAgeU.setTextColor(Color.BLACK)

        }











        design.txtProfF.setOnClickListener {
            design.txtProfF.setBackgroundResource(R.drawable.selected_text_design)
            design.txtProfF.setTextColor(Color.WHITE)
            design.txtNameF.setBackgroundResource(R.drawable.search_view_design)
            design.txtNameF.setTextColor(Color.BLACK)




            defaultFilterSearch = "prodf"
            design.searchViewFood.setQuery("",true)
        }

        design.txtNameF.setOnClickListener {


            design.txtNameF.setBackgroundResource(R.drawable.selected_text_design)
            design.txtNameF.setTextColor(Color.WHITE)


            design.txtProfF.setBackgroundResource(R.drawable.search_view_design)
            design.txtProfF.setTextColor(Color.BLACK)




            defaultFilterSearch = "name"
            design.searchViewFood.setQuery("",true)
        }

        design.bttnFilter.setOnClickListener {
            design.txtFWoman.setBackgroundResource(R.drawable.search_view_design)
            design.txtFWoman.setTextColor(Color.BLACK)

            design.textFMan.setBackgroundResource(R.drawable.search_view_design)
            design.textFMan.setTextColor(Color.BLACK)


            design.txtAgeU.setBackgroundResource(R.drawable.search_view_design)
            design.txtAgeU.setTextColor(Color.BLACK)



            design.txtAD.setBackgroundResource(R.drawable.search_view_design)
            design.txtAD.setTextColor(Color.BLACK)

            if (design.clFilter.visibility == View.GONE) {
                design.clFilter.visibility = View.VISIBLE
            }else{
                design.clFilter.visibility = View.GONE






            }
        }

    }


}