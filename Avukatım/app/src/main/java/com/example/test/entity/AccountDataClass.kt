package com.example.test.entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data  class AccountDataClass (@SerializedName("_id") @Expose var id:String,
                 @SerializedName("email") @Expose var email:String,
                 @SerializedName("accountType") @Expose var accountType:String) {
}