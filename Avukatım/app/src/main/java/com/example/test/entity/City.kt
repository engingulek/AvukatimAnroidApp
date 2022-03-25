package com.example.test.entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data  class City(@SerializedName("_id") @Expose var id:String,
                 @SerializedName("cityName") @Expose var cityName:String,
                 @SerializedName("district") @Expose var district:Array<String>) {
}