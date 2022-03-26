package com.example.test.entity

class Lawyer(var authUserId:String,
             var lawyerImageUrl:String,
             var lawyerNameSurname:String,
             var lawyerGender:String,
             var lawyerAge:String,
             var lawyerProfession:Array<String>,
             var lawyerLocation:String,
             var lawyerEstiOnliHours:String,
             var lawyerDescription:String,
             var lawyerLocCoordinate:Array<String>) {

    init {
        this.authUserId = authUserId
        this.lawyerImageUrl = lawyerImageUrl
        this.lawyerNameSurname = lawyerNameSurname
        this.lawyerGender = lawyerGender
        this.lawyerAge = lawyerAge
        this.lawyerLocCoordinate = lawyerLocCoordinate
        this.lawyerLocation = lawyerLocation
        this.lawyerEstiOnliHours = lawyerEstiOnliHours
        this.lawyerDescription = lawyerDescription




    }
}