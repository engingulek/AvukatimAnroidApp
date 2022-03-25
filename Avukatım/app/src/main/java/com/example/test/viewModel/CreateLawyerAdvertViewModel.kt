package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.City
import com.example.test.entity.Lawyer
import com.example.test.entity.Profession
import com.example.test.repo.MyLawyerRepository

class CreateLawyerAdvertViewModel : ViewModel() {
    val myLayyerDao = MyLawyerRepository()
    var cityList = MutableLiveData<List<City>>()
    var professionListe = MutableLiveData<List<Profession>>()



    init {
        load()
        cityList = myLayyerDao.bringCity()
        professionListe = myLayyerDao.bringProfession()
    }



    fun load() {
        myLayyerDao.getAllCity()
        myLayyerDao.getAllProfession()
    }


    /*fun createAdvert(lawyerImageUrl:String,
                     lawyerNameSurname :String,
                     lawyerGender:String,
                     lawyerAge:String,
                     lawyerProfession:Array<String>,
                     lawyerLocation :String,
                     lawyerEstiOnliHours:String,
                     lawyerDescription:String,
                     lawyerLocCoordinate:String) {
        myLayyerDao.addAdvertToLawyerAdvert(lawyerImageUrl,
            lawyerNameSurname,
            lawyerGender,
            lawyerAge,
            lawyerProfession,
            lawyerLocation,
            lawyerEstiOnliHours,
            lawyerDescription,
            lawyerLocCoordinate)
    }*/

    /* fun createAdvertViewModel(lawyerInfo:Lawyer) {
         myLayyerDao.createLawyerAdvertRepo(lawyerInfo)
     }*/

    fun createAdvert(a: Lawyer) {
        myLayyerDao.addAdvertToLawyerAdvert(a)
    }




}