package com.example.ospedidos.model.event

data class EventResponse(
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
