package com.example.test.retrofit

import com.example.test.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface MyLawyerDaoInterface {
    @GET("lawyerInfo")
    fun allLawyerInfo() : Call<LawyerInfoResult>

    @GET("lawyerComment")
    fun getLawyerComment(): Call<LawyerCommentResult>

    @GET("cities")
    fun getCity(): Call<CityResult>

    @GET("professions")
    fun getProfessions(): Call<ProfessionResult>

    //  @POST("lawyerRegister")
    //   @FormUrlEncoded
    /*fun createLawyerAdvertInterface(@Field("lawyerImageUrl") lawyerImageUrl:String,
                           @Field("lawyerNameSurname") lawyerNameSurname :String,
                           @Field("lawyerGender") lawyerGender:String,
                           @Field("lawyerAge") lawyerAge:String,
                           @Field("lawyerProfession") lawyerProfession:Array<String>,
                           @Field("lawyerLocation") lawyerLocation :String,
                           @Field("lawyerEstiOnliHours") lawyerEstiOnliHours:String,
                           @Field("lawyerDescription") lawyerDescription:String,
                           @Field("lawyerLocCoordinate") lawyerLocCoordinate:String
    ):Call<CRUDResult>*/




    @POST("/lawyerInfoAuth")
    fun getAuthInfoLawyer(@Body ada: Lawyer): Call<LawyerInfoResult>

    // delete
    @POST("/deleteAdvert")
    fun delete(@Body adaa : Lawyer) :Call<LawyerInfoResult>

    // favLawyer
    @GET("favLawyerInfo")
    fun getFavoriteLawyerInfo():Call<FavLawyerResult>


    @POST("lawyerRegister")
    fun createLawyerInterface(@Body test: Lawyer): Call<Lawyer>

   @POST("favlawyerRegister")
  fun addFavList(@Body a : Lawyer) : Call<Lawyer>


    @POST("/deletefavAdvert")
    fun favdelete(@Body adaa : Lawyer) :Call<FavLawyerResult>



















}