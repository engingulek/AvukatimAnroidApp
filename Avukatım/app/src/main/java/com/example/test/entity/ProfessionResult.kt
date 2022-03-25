package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class ProfessionResult(@SerializedName("professions") @Expose var professionsList:List<Profession>,
                       @SerializedName("success") @Expose  var success:Int) {
}