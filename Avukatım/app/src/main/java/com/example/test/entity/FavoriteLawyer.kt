package com.example.test.entity

class FavoriteLawyer(
    var favauthUserId : String,
    var favlawyerImageUrl: String,
    var favlawyerNameSurname: String,
    var favlawyerGender: String,
    var favlawyerAge: String,
    var favlawyerProfession: Array<String>,
    var favlawyerLocationCity: String,
    var favlawyerLocationCounty: String,
    var favlawyerEstiOnliHours: String,
    var favlawyerDescription: String,
    var favlawyerUniversty: String,
) {

    init {
        this.favauthUserId = favauthUserId
        this.favlawyerImageUrl= favlawyerImageUrl
        this.favlawyerNameSurname= favlawyerNameSurname
        this.favlawyerGender = favlawyerGender
        this.favlawyerAge = favlawyerAge
        this.favlawyerProfession = favlawyerProfession
        this.favlawyerLocationCity = favlawyerLocationCity
        this.favlawyerLocationCounty= favlawyerLocationCounty
        this.favlawyerEstiOnliHours = favlawyerEstiOnliHours
        this.favlawyerDescription = favlawyerDescription
        this.favlawyerUniversty = favlawyerUniversty




    }
}