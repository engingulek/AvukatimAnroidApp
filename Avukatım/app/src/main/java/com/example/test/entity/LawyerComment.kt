package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LawyerComment(@SerializedName("_id") @Expose var id:String,
                         @SerializedName("customerId") @Expose var customerId:String,
                         @SerializedName("lawyerId") @Expose var lawyerId:String,
                         @SerializedName("commnets") @Expose var commnets:Array<String>,
                         @SerializedName("commentDate") @Expose var commentDate:String,){
}