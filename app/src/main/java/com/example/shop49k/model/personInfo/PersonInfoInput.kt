package com.example.shop49k.model.personInfo

data class PersonInfoInput (
    var full_name : String,
    var dob : String,
    var fontside_image_identity_cart : String?,
    var backside_image_identity_cart : String?,
    var gender : String?,
    var city_id : String?,
    var district_id : String?,
    var commune_id : String?,
    var address : String?,
    var shop_created : String?,
    var description : String?,
    var shop_address : String?,
    var business_area : String?,
    var avatar : String?,
    var merchant_registration_front_site : String?,
    var merchant_registration_back_site : String?,
    var bank_account_number : String?,
    var bank_id : String?,
    var shop_city_id : String?,
    var shop_district_id : String?,
    var shop_commune_id : String?
)