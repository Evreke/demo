package ru.evreke.demo.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import ru.evreke.demo.entity.Booking

interface BookingRepository : CrudRepository<Booking, Long> {
    fun findAllByUserId(id: Long): List<Booking>

    @Transactional
    fun deleteBookingByUserIdAndId(userId: Long, bookingId: Long)
}