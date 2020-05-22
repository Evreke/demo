package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.repository.BookingRepository

@RestController
@RequestMapping("/api/v1/users")
class UserApi(
    private val bookingRepo: BookingRepository
) {
    @GetMapping("/{id}/bookings")
    fun getAllUserBookings(
        @PathVariable id: Long
    ): List<Booking> {
        return bookingRepo.findAllByUserId(id)
    }
}