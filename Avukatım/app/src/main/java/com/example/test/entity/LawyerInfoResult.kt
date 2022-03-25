package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LawyerInfoResult(@SerializedName("lawyerInfos") @Expose var lawyerInfoList:List<LawyerInfo>,
                       @SerializedName("success") @Expose  var success:Int) {
}