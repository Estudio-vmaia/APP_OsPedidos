package com.example.ospedidos.model.product

data class ProductResponse(
    val produtos: List<Product>
)

data class Product(
    val item: String,
    val id: String,
    val preco: String
)

