package com.example.test.entity

class Lawyer(var lawyerImageUrl:String,
             var lawyerNameSurname:String,
             var lawyerGender:String,
             var lawyerAge:String,
             var lawyerProfession:Array<String>,
             var lawyerLocation:String,
             var lawyerEstiOnliHours:String,
             var lawyerDescription:String,
             var lawyerLocCoordinate:String) {

    init {
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