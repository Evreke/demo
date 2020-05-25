package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.exceptions.AlreadyPayedException
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
        val movieSession = movieSessionRepo.findById(movieSessionId).orElseThrow { NotFoundException("Movie session with id=$movieSessionId not found") }.also {
            it.booked++
        }
        val user = userRepo.findById(userId).orElseThrow { NotFoundException("User with id=$userId not found") }
        repo.save(Booking().also {
            it.session = movieSession
            it.user = user
        })
    }

    @PutMapping("/{id}")
    fun setPayed(
        @PathVariable id: Long
    ) {
        val booking = repo.findById(id).orElseThrow { NotFoundException("Booking with id=$id not found") }
        if (booking.payed) {
            throw AlreadyPayedException("Booking with id=$id was already payed")
        } else {
            booking.apply {
                payed = !payed
                session!!.booked--
                session!!.occupancy++
            }
            repo.save(booking)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(
        @RequestParam(required = false) userId: Long?,
        @PathVariable id: Long
    ) {
        val booking = repo.findById(id).orElseThrow { NotFoundException("Booking with id=$id not found") }
        if (booking.payed) {
            throw AlreadyPayedException("Booking with id=$id was payed. You shouldn't delete it!")
        } else {
            movieSessionRepo.save(booking.session!!.also { it.booked-- })
            userId?.let { repo.deleteBookingByUserIdAndId(userId, id) } ?: repo.deleteById(id)
        }
    }
}