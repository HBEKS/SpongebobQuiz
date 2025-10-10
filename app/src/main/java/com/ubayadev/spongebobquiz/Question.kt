package com.ubayadev.spongebobquiz

data class Question(
    var question:String,
    var answer: Boolean,
    var imageId:Int,
    var url:String = "",
    var isAvailable:Boolean = true
)