package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.entity.User

interface BookingService {
    fun createBooking(movieSession: MovieSession, user: User): Booking
    fun getBooking(id: Long): Booking
    fun getAllBookings(): MutableIterable<Booking>
    fun deleteBooking(id: Long, userId: Long?)
    fun payBooking(id: Long)
    fun getUserBookings(userId: Long, isPayed: Boolean): MutableIterable<Booking>
}