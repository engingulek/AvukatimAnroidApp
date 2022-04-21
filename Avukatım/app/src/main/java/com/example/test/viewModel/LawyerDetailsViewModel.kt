package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.entity.LawyerComment
import com.example.test.entity.Meeting
import com.example.test.repo.MyLawyerRepository

class LawyerDetailsViewModel : ViewModel() {
    var lawyerCommentList = MutableLiveData<List<LawyerComment>>()
    val myLawyerRepo = MyLawyerRepository()

    init {
        load()
        lawyerCommentList = myLawyerRepo.bringLawyerComments()
    }
    fun load() {
        myLawyerRepo.getAllLawyerComment()
    }

    fun addMeetingInfo(meet:Meeting) {
        myLawyerRepo.meetingAddToMeetList(meet)
    }
}