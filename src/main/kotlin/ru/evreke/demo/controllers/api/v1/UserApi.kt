package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.services.interfaces.BookingService
import ru.evreke.demo.services.interfaces.MovieSessionService

@RestController
@RequestMapping("/api/v1/users")
class UserApi(
    private val bookingService: BookingService,
    private val movieSessionService: MovieSessionService
) {
    @GetMapping("/{id}/bookings")
    fun getAllUserBookings(
        @PathVariable id: Long,
        @RequestParam(required = false, defaultValue = "false") isPayed: Boolean
    ): MutableIterable<Booking> {
        return bookingService.getUserBookings(id, isPayed)
    }

    @GetMapping("/{id}/movie-sessions")
    fun getAllUserSessions(
        @PathVariable id: Long
    ): MutableIterable<MovieSession> {
        return movieSessionService.getUserMovieSessions(id)
    }
}