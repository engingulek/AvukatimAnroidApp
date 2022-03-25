package com.example.test
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.Callback

interface TestInterface {
    @POST("lawyerRegister")
    @FormUrlEncoded
    fun createLawyerAdvertInterface(@Field("lawyerImageUrl") lawyerImageUrl:String,
                                    @Field("lawyerName") lawyerName :String,
                                    @Field("lawyerSurname") lawyerSurname:String,
                                    @Field("lawyerGender") lawyerGender:String,
                                    @Field("lawyerAge") lawyerAge:String,
                                    @Field("lawyerProfession") lawyerProfession:Array<String>,
                                    @Field("lawyerLocation") lawyerLocation :String,
                                    @Field("lawyerEstiOnliHours") lawyerEstiOnliHours:String,
                                    @Field("lawyerDescription") lawyerDescription:String,
                                    @Field("lawyerLocCoordinate") lawyerLocCoordinate:String
    ):Call<CRUDResult>



    @POST("lawyerRegister")
    fun test(@Body test: Test):Call<Test>


}