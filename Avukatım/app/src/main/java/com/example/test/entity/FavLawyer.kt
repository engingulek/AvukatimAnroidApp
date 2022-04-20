package com.example.test.entity

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class FavLawyer(@SerializedName("_id") @Expose var id:String,
                @SerializedName("favauthUserId") @Expose var favauthUserId:String,
                @SerializedName("favlawyerImageUrl") @Expose var favlawyerImageUrl:String,
                @SerializedName("favlawyerNameSurname") @Expose var favlawyerNameSurname:String,
                @SerializedName("favlawyerGender") @Expose var favlawyerGender:String,
                @SerializedName("favlawyerAge") @Expose var favlawyerAge:String,
                @SerializedName("favlawyerProfession") @Expose var favlawyerProfession:Array<String>,
                @SerializedName("favlawyerLocationCity") @Expose var favlawyerLocationCity:String,
                @SerializedName("favlawyerLocationCounty") @Expose var favlawyerLocationCounty:String,
                @SerializedName("favlawyerEstiOnliHours") @Expose var favlawyerEstiOnliHours:String,
                @SerializedName("favlawyerDescription") @Expose var favlawyerDescription:String,
                @SerializedName("favlawyerUniversty") @Expose var favlawyerUniversty:String):Serializable {
}