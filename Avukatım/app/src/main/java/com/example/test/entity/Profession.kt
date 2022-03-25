package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class Profession(@SerializedName("_id") @Expose var id:String,
                      @SerializedName("professionName") @Expose var professionName:String,
                      @SerializedName("professionDescription") @Expose var professionDescription:String) {
}