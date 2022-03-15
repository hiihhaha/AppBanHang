package com.kotlin.appbanhang.model

import java.io.Serializable

class UserResponse(
    var success: Boolean?,
    var message: String?,
    var result: List<User>?
)

class User(
    var id : String? = null,
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var numberphone: String? = null
) : Serializable
