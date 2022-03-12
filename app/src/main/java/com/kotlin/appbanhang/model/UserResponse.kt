package com.kotlin.appbanhang.model

import java.io.Serializable

class UserResponse (
    var success : Boolean?,
    var message : String?
)
class User (
    var emai : String,
    var password : String,
    var username : String,
    var numberphone : Int
        ) : Serializable
