package com.example.test.entity

class Meeting(var userId:String,
              var advertId:String,
              var date:String,
              var time:String) {

    init {
        this.userId = userId
        this.advertId = advertId
        this.date = date
        this.time = time
    }

}