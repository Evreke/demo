package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Booking

interface BookingService {
    fun createBooking(movieSessionId: Long, userId: Long)
    fun getBooking(id: Long): Booking
    fun getAllBookings(): MutableIterable<Booking>
    fun deleteBooking(id: Long, userId: Long?)
    fun payBooking(id: Long)
    fun getUserBookings(userId: Long, isPayed: Boolean): MutableIterable<Booking>
}