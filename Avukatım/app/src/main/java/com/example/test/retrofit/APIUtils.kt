package com.example.test.retrofit

class APIUtils {

    companion object {
        val BASE_URL = "http://10.0.2.2:3000/"


        fun getMyLawyerDaoInterface() : MyLawyerDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(MyLawyerDaoInterface::class.java)
        }

    }
}