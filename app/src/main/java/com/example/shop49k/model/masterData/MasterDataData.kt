package com.example.shop49k.model.masterData

data class MasterDataData (

	val categories : List<Categories>,
	val offer_status : Offer_status,
	val gender : Gender,
	val sort_field : Sort_field,
	val sort_order : Sort_order,
	val discount_type : Discount_type,
	val bank_list : List<Bank_list>
)