package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.repository.BookingRepository
import ru.evreke.demo.repository.MovieSessionRepository

@RestController
@RequestMapping("/api/v1/users")
class UserApi(
    private val bookingRepo: BookingRepository,
    private val movieSessionRepo: MovieSessionRepository
) {
    @GetMapping("/{id}/bookings")
    fun getAllUserBookings(
        @PathVariable id: Long,
        @RequestParam(required = false, defaultValue = "false") payed: Boolean
    ): List<Booking> {
        return bookingRepo.findAllByUserIdAndPayedIs(id, payed)
    }

    @GetMapping("/{id}/movie-sessions")
    fun getAllUserSessions(
        @PathVariable id: Long
    ): List<MovieSession> {
        return movieSessionRepo.findSessionsByUserId(id)
    }
}