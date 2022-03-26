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
                      @SerializedName("lawyerLocation") @Expose var lawyerLocation:String,
                      @SerializedName("lawyerEstiOnliHours") @Expose var lawyerEstiOnliHours:String,
                      @SerializedName("lawyerDescription") @Expose var lawyerDescription:String,
                      @SerializedName("lawyerLocCoordinate") @Expose var lawyerLocCoordinate:Array<String>


):Serializable {
}