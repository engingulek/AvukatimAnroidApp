package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.MeetingDataClass
import com.example.test.repo.MyLawyerRepository
import com.google.firebase.auth.FirebaseAuth

class LawyerMeetingListViewModel : ViewModel() {
    var meetingList = MutableLiveData<List<MeetingDataClass>>()
    val myLawyerRepo = MyLawyerRepository()


    init {
        load()
        meetingList = myLawyerRepo.bringMeetingList()
    }

    fun load(){
        myLawyerRepo.getAllMeetingListRepo()
    }

}