package com.example.test.repo

import androidx.lifecycle.MutableLiveData
import com.example.test.entity.*
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLawyerRepository {
    var myLawyerDao : MyLawyerDaoInterface
    //list
    var lawyerInfoList : MutableLiveData<List<LawyerInfo>>
    var lawyerCommnetList: MutableLiveData<List<LawyerComment>>
    val citylist : MutableLiveData<List<City>>
    val professionList: MutableLiveData<List<Profession>>
    private lateinit var  call : Call<Lawyer>

    init {
        myLawyerDao = APIUtils.getMyLawyerDaoInterface()
        //list
        lawyerInfoList = MutableLiveData()
        lawyerCommnetList = MutableLiveData()
        citylist = MutableLiveData()
        professionList = MutableLiveData()
    }

    fun bringLawyerInfos() : MutableLiveData<List<LawyerInfo>> {
        return lawyerInfoList
    }

    fun bringLawyerComments() : MutableLiveData<List<LawyerComment>> {
        return lawyerCommnetList
    }

    fun bringCity() : MutableLiveData<List<City>> {
        return  citylist
    }

    fun bringProfession(): MutableLiveData<List<Profession>> {
        return professionList
    }

    fun getAllLawyerInfo() {
        myLawyerDao.allLawyerInfo().enqueue(object: Callback<LawyerInfoResult> {
            override fun onResponse(call: Call<LawyerInfoResult>, response: Response<LawyerInfoResult>) {
                val liste = response.body().lawyerInfoList
                lawyerInfoList.value = liste
            }

            override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

            }

        })
    }


    fun getAllLawyerComment() {
        myLawyerDao.getLawyerComment().enqueue(object : Callback<LawyerCommentResult> {
            override fun onResponse(
                call: Call<LawyerCommentResult>,
                response: Response<LawyerCommentResult>
            ) {
                val liste = response.body().lawyerCommentList
                lawyerCommnetList.value = liste

            }

            override fun onFailure(call: Call<LawyerCommentResult>?, t: Throwable?) {

            }

        })
    }


    fun getAllCity() {
        myLawyerDao.getCity().enqueue(object : Callback<CityResult> {
            override fun onResponse(call: Call<CityResult>, response: Response<CityResult>) {
                val liste = response.body().cityList
                citylist.value = liste
            }

            override fun onFailure(call: Call<CityResult>, t: Throwable) {

            }

        })
    }

    fun getAllProfession() {
        myLawyerDao.getProfessions().enqueue(object : Callback<ProfessionResult> {
            override fun onResponse(
                call: Call<ProfessionResult>,
                response: Response<ProfessionResult>
            ) {
                val liste = response.body().professionsList
                professionList.value = liste

            }

            override fun onFailure(call: Call<ProfessionResult>?, t: Throwable?) {
            }

        })
    }


    /*fun addAdvertToLawyerAdvert(lawyerImageUrl:String,
     lawyerNameSurname :String,
     lawyerGender:String,
 lawyerAge:String,
    lawyerProfession:Array<String>,
     lawyerLocation :String,
    lawyerEstiOnliHours:String,
   lawyerDescription:String,
    lawyerLocCoordinate:String) {
        myLawyerDao.createLawyerAdvertInterface(lawyerImageUrl,
            lawyerNameSurname,
            lawyerGender,
            lawyerAge,
            lawyerProfession,
        lawyerLocation,
        lawyerEstiOnliHours,
        lawyerDescription,
        lawyerLocCoordinate).enqueue(object :Callback<CRUDResult>{
            override fun onResponse(call: Call<CRUDResult>?, response: Response<CRUDResult>?) {

            }

            override fun onFailure(call: Call<CRUDResult>, t: Throwable) {

                Log.e("Hatask","${t.message}")
            }

        })
    }*/


    fun addAdvertToLawyerAdvert(a:Lawyer) {
        call =myLawyerDao.createLawyerInterface(a)
        call.enqueue(object : Callback<Lawyer> {
            override fun onResponse(call: Call<Lawyer>?, response: Response<Lawyer>?) {

            }

            override fun onFailure(call: Call<Lawyer>?, t: Throwable?) {

            }

        })

    }








}

