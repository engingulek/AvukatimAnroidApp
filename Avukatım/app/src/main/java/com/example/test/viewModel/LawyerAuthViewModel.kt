package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.City
import com.example.test.entity.LawyerInfo
import com.example.test.entity.Profession
import com.example.test.repo.MyLawyerRepository

class LawyerAuthViewModel() : ViewModel() {
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




}

