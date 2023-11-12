package com.example.ospedidos.presentation.model.event

data class EventResponse(
    val arrayName: String,
    val arrayColunas: String,
    val arrayKeys: String,
    val eventos: List<Event>
)

data class Event(
    val nome: String,
    val id: String,
    val dataini: String,
    val horaini: String,
    val datafim: String,
    val horafim: String,
    val acumulativo: String,
    val tipocob: String,
    val valorminimo: String?,
    val status: String
)
