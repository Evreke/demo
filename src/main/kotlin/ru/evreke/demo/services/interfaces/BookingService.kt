package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.entity.User

interface BookingService {
    fun createBooking(movieSession: MovieSession, user: User): Booking
    fun getBooking(id: Long): Booking
    fun getAllBookings(): MutableIterable<Booking>
    fun deleteBooking(booking: Booking, user: User?)
    fun payBooking(booking: Booking) : Booking
    fun getUserBookings(user: User, isPayed: Boolean): MutableIterable<Booking>
}