package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class CityResult (@SerializedName("city") @Expose var cityList:List<City>,
                  @SerializedName("success") @Expose  var success:Int) {
}