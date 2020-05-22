package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.BookingRepository
import ru.evreke.demo.repository.MovieSessionRepository
import ru.evreke.demo.repository.UserRepository

@RestController
@RequestMapping("/api/v1/bookings")
class BookingApi(
    private val repo: BookingRepository,
    private val userRepo: UserRepository,
    private val movieSessionRepo: MovieSessionRepository
) {
    @GetMapping("/", "")
    fun getAllBookings(): MutableIterable<Booking> {
        return repo.findAll()
    }

    @PostMapping("/", "")
    fun createBooking(
        @RequestParam movieSessionId: Long,
        @RequestParam userId: Long
    ) {
        val movieSession = movieSessionRepo.findById(movieSessionId).orElseThrow { NotFoundException("Movie session with id=$movieSessionId not found") }
        val user = userRepo.findById(userId).orElseThrow { NotFoundException("User with id=$userId not found") }
        repo.save(Booking().also {
            it.session = movieSession
            it.user = user
        })
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(
        @RequestParam(required = false) userId: Long?,
        @PathVariable id: Long
    ) {
        userId?.let { repo.deleteBookingByUserIdAndId(userId, id) } ?: repo.deleteById(id)
    }
}