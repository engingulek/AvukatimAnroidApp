package com.example.test.entity
import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class MeetingDataClass(@SerializedName("_id") @Expose var id:String,
                            @SerializedName("userId") @Expose var userId:String,
                            @SerializedName("advertId") @Expose var advertId:String,
                            @SerializedName("date") @Expose var date:String,
                            @SerializedName("time") @Expose var time:String,
                            @SerializedName("lawyerAuthUserId") @Expose var lawyerAuthUserId:String,
                            @SerializedName("lawyerNameSurname") @Expose var lawyerNameSurname:String,
                            @SerializedName("lawyerImageUrl") @Expose var lawyerImageUrl:String,
                            @SerializedName("clientNameSurname") @Expose var clientNameSurname:String,
                            @SerializedName("clientImageUrl") @Expose var clientImageUrl:String,





):Serializable {
}