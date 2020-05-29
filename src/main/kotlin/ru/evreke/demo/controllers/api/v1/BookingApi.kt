package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.services.interfaces.BookingService
import ru.evreke.demo.services.interfaces.MovieSessionService
import ru.evreke.demo.services.interfaces.UserService

@RestController
@RequestMapping("/api/v1/bookings")
class BookingApi(
    private val bookingService: BookingService,
    private val movieSessionService: MovieSessionService,
    private val userService: UserService
) {
    @GetMapping("/", "")
    fun getAllBookings(): MutableIterable<Booking> {
        return bookingService.getAllBookings()
    }

    @PostMapping("/", "")
    fun createBooking(
        @RequestParam movieSessionId: Long,
        @RequestParam userId: Long
    ) {
        val movieSession = movieSessionService.getMovieSession(movieSessionId)
        val user = userService.getUser(userId)
        bookingService.createBooking(movieSession, user)
    }

    @PutMapping("/{id}/pay")
    fun setPayed(
        @PathVariable id: Long
    ) {
        bookingService.payBooking(bookingService.getBooking(id))
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(
        @RequestParam(required = false) userId: Long?,
        @PathVariable id: Long
    ) {
        bookingService.deleteBooking(
            bookingService.getBooking(id),
            userId?.let { userService.getUser(userId) }
        )
    }
}
