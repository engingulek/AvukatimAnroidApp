package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.LawyerInfo
import com.example.test.repo.MyLawyerRepository

class HomePageViewModel : ViewModel() {
    var lawyerInfoList = MutableLiveData<List<LawyerInfo>>()
    val myLawyerRepo = MyLawyerRepository()

    init {
        load()
        lawyerInfoList = myLawyerRepo.bringLawyerInfos()
    }

    fun load() {
        myLawyerRepo.getAllLawyerInfo()
    }
}