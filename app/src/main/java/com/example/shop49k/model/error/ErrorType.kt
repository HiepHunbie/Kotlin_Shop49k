package com.example.shop49k.model.error

data class ErrorType(
    val identity: String,
    val full_name: String,
    val email: String,
    val phone: String,
    val password: String,
    val identity_number: String,
    val shop_name: String,
    val shop_city_id: String,
    val shop_district_id: String,
    val shop_commune_id: String,
    val business_area: String,
    val group_id: String,
    val merchant_id: String,
    val reset_code: String,
    val old_password: String,
    val new_password: String,
    val new_password_confirm: String,
    val title: String,
    val start_time: String,
    val end_time: String,
    val how_to_use: String,
    val apply_condition: String,
    val offer_number: String,
    val type: String,
    val status: String,
    val discount_type: String,
    val discount_value: String,
    val discount_note: String,
    val offer_id: String,
    val dob: String,
    val gender: String,
    val fontside_image_identity_cart: String,
    val backside_image_identity_cart: String,
    val avatar: String,
    val city_id: String,
    val district_id: String,
    val commune_id: String,
    val address: String,
    val shop_created: String,
    val description: String,
    val shop_address: String,
    val merchant_registration_front_site: String,
    val merchant_registration_back_site: String,
    val bank_id: String,
    val bank_account_number: String
)