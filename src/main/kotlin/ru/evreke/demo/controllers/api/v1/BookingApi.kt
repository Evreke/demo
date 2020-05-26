package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.services.interfaces.BookingService

@RestController
@RequestMapping("/api/v1/bookings")
class BookingApi(
    private val bookingService: BookingService
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
        bookingService.createBooking(movieSessionId, userId)
    }

    @PutMapping("/{id}/pay")
    fun setPayed(
        @PathVariable id: Long
    ) {
        bookingService.payBooking(id)
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(
        @RequestParam(required = false) userId: Long?,
        @PathVariable id: Long
    ) {
        bookingService.deleteBooking(id, userId)
    }
}
