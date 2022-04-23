package com.example.test.entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MeetingDataClassResult(@SerializedName("meetings") @Expose var meetingsList:List<MeetingDataClass>,
                             @SerializedName("success") @Expose  var success:Int) {
}