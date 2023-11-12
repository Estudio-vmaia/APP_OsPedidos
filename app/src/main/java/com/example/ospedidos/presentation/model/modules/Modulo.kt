package com.example.ospedidos.presentation.model.modules

data class ModuloResponse(
    val modulos: List<Modulo>
)

data class Modulo(
    val nomeModulo: String,
    val urlModulo: String,
    val id: String,
    val idModulo: String,
    val idCliente: String,
    val cliente: String,
    val slug: String,
    val embed: String,
    val ativo: String,
    val validade: String,
    val dataCadastro: String
)
