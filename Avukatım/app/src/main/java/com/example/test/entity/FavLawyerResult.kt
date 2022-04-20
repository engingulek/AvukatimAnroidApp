package com.example.test.entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class FavLawyerResult(@SerializedName("favLawyerInfos") @Expose var favlawyerInfoList:List<LawyerInfo>,
                      @SerializedName("success") @Expose  var success:Int) {
}