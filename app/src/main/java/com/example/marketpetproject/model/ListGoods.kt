package com.example.marketpetproject.model

data class ListGoods(
    var products : List<Goods>,
    var total    : Int?,
    var skip     : Int?,
    var limit    : Int?
)