package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Hall

interface HallService {
    fun createHall(hall: Hall)
    fun getHall(id: Long): Hall
    fun getAllHalls(): MutableIterable<Hall>
    fun deleteHall(id: Long)
    fun updateHall(id: Long, hall: Hall)
}