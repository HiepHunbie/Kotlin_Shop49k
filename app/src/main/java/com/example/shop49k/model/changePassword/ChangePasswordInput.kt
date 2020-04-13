package com.example.shop49k.model.changePassword

data class ChangePasswordInput (
    var old_password : String,
    var new_password : String,
    var new_password_confirm : String
)