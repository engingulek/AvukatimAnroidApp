package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.LawyerInfo
import com.example.test.repo.MyLawyerRepository

class FavListPageViewModel:ViewModel() {
    var favlawyerInfoList = MutableLiveData<List<LawyerInfo>>()
    val myLawyerRepo = MyLawyerRepository()

    init {
        load()
        favlawyerInfoList = myLawyerRepo.bringFavLawyer()
    }

    fun load() {
        myLawyerRepo.getAllFavLawyerInfo()
    }
}