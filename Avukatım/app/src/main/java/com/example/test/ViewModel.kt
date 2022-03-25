package com.example.test
import androidx.lifecycle.ViewModel
class ViewModel :ViewModel() {
    val repo = Repo()

    fun createAdvert(lawyerImageUrl:String,
                     lawyerName :String,
                     lawyerSurname:String,
                     lawyerGender:String,
                     lawyerAge:String,
                     lawyerProfession:Array<String>,
                     lawyerLocation :String,
                     lawyerEstiOnliHours:String,
                     lawyerDescription:String,
                     lawyerLocCoordinate:String) {
        repo.addAdvertToLawyerAdvert(lawyerImageUrl,
            lawyerName,
            lawyerSurname,
            lawyerGender,
            lawyerAge,
            lawyerProfession,
            lawyerLocation,
            lawyerEstiOnliHours,
            lawyerDescription,
            lawyerLocCoordinate)
    }


    fun aka(a:Test) {
        repo.deneme(a)
    }

}