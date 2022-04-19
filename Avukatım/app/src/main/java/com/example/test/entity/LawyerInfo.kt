package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable


data class LawyerInfo(@SerializedName("_id") @Expose var id:String,
                      @SerializedName("authUserId") @Expose var authUserId:String,
                      @SerializedName("lawyerImageUrl") @Expose var lawyerImageUrl:String,
                      @SerializedName("lawyerNameSurname") @Expose var lawyerNameSurname:String,
                      @SerializedName("lawyerGender") @Expose var lawyerGender:String,
                      @SerializedName("lawyerAge") @Expose var lawyerAge:String,
                      @SerializedName("lawyerProfession") @Expose var lawyerProfession:Array<String>,
                      @SerializedName("lawyerLocationCity") @Expose var lawyerLocationCity:String,
                      @SerializedName("lawyerLocationCounty") @Expose var lawyerLocationCounty:String,
                      @SerializedName("lawyerEstiOnliHours") @Expose var lawyerEstiOnliHours:String,
                      @SerializedName("lawyerDescription") @Expose var lawyerDescription:String,
                      @SerializedName("lawyerUniversty") @Expose var lawyerUniversty:String,





):Serializable {
}