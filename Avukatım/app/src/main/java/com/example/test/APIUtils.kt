package com.example.test

class APIUtils {
    companion object {
        val BASE_URL = "http://10.0.2.2:3000"


        fun getMyLawyerDaoInterface() : TestInterface {
            return RetrofitClient.getClient(BASE_URL).create(TestInterface::class.java)
        }

    }
}