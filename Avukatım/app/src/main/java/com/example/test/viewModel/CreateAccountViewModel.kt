package com.example.test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.entity.Account
import com.example.test.entity.AccountDataClass
import com.example.test.repo.MyLawyerRepository

class CreateAccountViewModel : ViewModel() {
    val myLawyerRepo = MyLawyerRepository()
    var accountList = MutableLiveData<List<AccountDataClass>>()


    init {
        load()
        accountList = myLawyerRepo.bringAccountList()
    }

    fun load() {
        myLawyerRepo.getAllAccountListRepo()
    }

    fun createAccount(newAccount : Account) {
        myLawyerRepo.createAccountToDatabase(newAccount)
    }
}