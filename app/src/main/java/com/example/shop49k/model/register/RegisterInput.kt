package com.example.shop49k.model.register

data class RegisterInput (
    var full_name : String,
    var phone : String,
    var email : String,
    var password : String,
    var re_password : String,
    var identity_number : String,
    var merchant_id : String,
    var business_area : String,
    var shop_city_id : String,
    var shop_district_id : String,
    var shop_address : String,
    var shop_commune_id : String,
    var group_id : String,
    var shop_name : String
)