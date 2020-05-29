package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.User

interface BookingRepository : CrudRepository<Booking, Long> {
    fun findAllByUserAndPayedIs(user: User, payed: Boolean): MutableIterable<Booking>

    @Transactional
    @Modifying
    @Query("delete from Booking as b where b.user = ?1 and b = ?2")
    fun deleteByBookingAndUser(user: User, booking: Booking)
}