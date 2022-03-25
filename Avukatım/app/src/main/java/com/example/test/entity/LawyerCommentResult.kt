package com.example.test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LawyerCommentResult(@SerializedName("lawyerComments") @Expose var lawyerCommentList:List<LawyerComment>,
                          @SerializedName("success") @Expose  var success:Int) {
}