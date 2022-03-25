package com.example.test

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repo {
    var testInterface :  TestInterface
    private lateinit var call : Call<Test>
    init {
        testInterface = APIUtils.getMyLawyerDaoInterface()
    }


    fun addAdvertToLawyerAdvert(lawyerImageUrl:String,
                                lawyerName :String,
                                lawyerSurname:String,
                                lawyerGender:String,
                                lawyerAge:String,
                                lawyerProfession:Array<String>,
                                lawyerLocation :String,
                                lawyerEstiOnliHours:String,
                                lawyerDescription:String,
                                lawyerLocCoordinate:String) {
        testInterface.createLawyerAdvertInterface(lawyerImageUrl,
            lawyerName,
            lawyerSurname,
            lawyerGender,
            lawyerAge,
            lawyerProfession,
            lawyerLocation,
            lawyerEstiOnliHours,
            lawyerDescription,
            lawyerLocCoordinate).enqueue(object : Callback<CRUDResult> {
            override fun onResponse(call: Call<CRUDResult>?, response: Response<CRUDResult>?) {

            }

            override fun onFailure(call: Call<CRUDResult>, t: Throwable) {

                Log.e("Hatask","${t.message}")
            }

        })
    }


    fun deneme(a:Test) {
        call = testInterface.test(a)
        call.enqueue(object : Callback<Test>{
            override fun onResponse(call: Call<Test>?, response: Response<Test>?) {

            }

            override fun onFailure(call: Call<Test>?, t: Throwable?) {

            }

        })

    }


}