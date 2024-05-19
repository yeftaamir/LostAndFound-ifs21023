package com.ifs21023.lostandfound.data.remote.response

import com.google.gson.annotations.SerializedName

data class DelcomAddLostFoundResponse(

	@field:SerializedName("data")
	val data: DataAddLostFoundResponse,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataAddLostFoundResponse(

	@field:SerializedName("todo_id")
	val lostfoundId: Int
)
