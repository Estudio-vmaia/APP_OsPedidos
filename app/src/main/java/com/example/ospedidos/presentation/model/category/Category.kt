package com.example.ospedidos.presentation.model.category

data class CategoryResponse(
    val categorias: List<Category>
)

data class Category(
    val nome: String,
    val id: String,
    val id_evento: String,
    val id_categoria: String,
    val id_produto: String,
    val ativo: String,
    val status: String,
    val data: String
)

