package com.example.ospedidos.presentation.model.product

data class ProductResponse(
    val produtos: List<Product>
)

data class Product(
    val item: String,
    val id: String,
    val preco: String
)

