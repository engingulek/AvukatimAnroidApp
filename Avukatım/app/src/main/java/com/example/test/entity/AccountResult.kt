package com.example.test.entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class AccountResult(@SerializedName("accounts") @Expose var accountsList:List<AccountDataClass>,
                    @SerializedName("success") @Expose  var success:Int) {
}