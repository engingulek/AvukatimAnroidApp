package com.example.test.entity

class Lawyer(
    var authUserId : String,
    var lawyerImageUrl: String,
    var lawyerNameSurname: String,
    var lawyerGender: String,
    var lawyerAge: String,
    var lawyerProfession: Array<String>,
    var lawyerLocationCity: String,
    var lawyerLocationCounty: String,
    var lawyerEstiOnliHours: String,
    var lawyerDescription: String,
    var lawyerUniversty: String
) {

    init {
        this.authUserId = authUserId
        this.lawyerImageUrl= lawyerImageUrl
        this.lawyerNameSurname= lawyerNameSurname
        this.lawyerGender = lawyerGender
        this.lawyerAge = lawyerAge
        this.lawyerProfession = lawyerProfession
        this.lawyerLocationCity = lawyerLocationCity
        this.lawyerLocationCounty= lawyerLocationCounty
        this.lawyerEstiOnliHours = lawyerEstiOnliHours
        this.lawyerDescription = lawyerDescription
        this.lawyerUniversty = lawyerUniversty




    }
}