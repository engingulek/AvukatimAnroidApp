package com.example.test.entity

class Account(var email:String,
              var accountType:String,
              var nameSurname :String,
              var userId : String
              )


{

    init {
        this.email = email
        this.accountType = accountType
        this.nameSurname = nameSurname
        this.userId = userId
    }

}