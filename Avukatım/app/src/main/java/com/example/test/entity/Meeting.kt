package com.example.test.entity

class Meeting(var userId:String,
              var advertId:String,
              var date:String,
              var time:String,
              var lawyerAuthUserId:String,
              var lawyerNameSurname:String,
              var lawyerImageUrl:String,
              var clientNameSurname:String,
              var clientImageUrl:String,
) {

    init {
        this.userId = userId
        this.advertId = advertId
        this.date = date
        this.time = time
        this.lawyerAuthUserId = lawyerAuthUserId
        this.lawyerImageUrl = lawyerImageUrl
        this.clientNameSurname = clientNameSurname
        this.clientImageUrl = clientImageUrl
    }

}