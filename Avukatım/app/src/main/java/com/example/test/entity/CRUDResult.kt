package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CRUDResult(@SerializedName("success") @Expose var success:Int,
                      @SerializedName("message") @Expose var message:String) {

}