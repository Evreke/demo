package ru.evreke.demo.services

import org.springframework.stereotype.Service
import ru.evreke.demo.entity.Booking
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.entity.User
import ru.evreke.demo.exceptions.AlreadyPayedException
import ru.evreke.demo.exceptions.BookingException
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.BookingRepository
import ru.evreke.demo.services.interfaces.BookingService
import ru.evreke.demo.services.interfaces.MovieSessionService
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class BookingServiceImpl(
    private val repo: BookingRepository,
    private val movieSessionService: MovieSessionService
) : BookingService {
    @Throws(BookingException::class)
    override fun createBooking(movieSession: MovieSession, user: User): Booking {
        val isUserPrivileged = user.category?.discount != BigDecimal.ZERO
        val saleToEveryone = movieSession.startSellingAt <= LocalDateTime.now()
        if (!movieSession.privileged || saleToEveryone || isUserPrivileged) {
            return repo.save(
                Booking(session = movieSession).also {
                    it.session.booked++
                    it.user = user
                    it.totalPrice = evaluateTotalPrice(user, movieSession)
                }
            )
        } else {
            throw BookingException("Tickets sale start at ${movieSession.startSellingAt}")
        }
    }

    override fun getBooking(id: Long): Booking {
        return repo.findById(id).orElseThrow { NotFoundException("Booking with id=$id was not found") }
    }

    override fun getAllBookings(): MutableIterable<Booking> {
        return repo.findAll()
    }

    override fun deleteBooking(id: Long, userId: Long?) {
        val booking = getBooking(id)
        if (booking.payed) {
            throw AlreadyPayedException("Booking with id=$id was payed. You shouldn't delete it!")
        } else {
            movieSessionService.updateMovieSession(booking.session.also { it.booked-- })
            userId?.let { repo.deleteBookingByIdAndUserId(userId, id) } ?: repo.deleteById(id)
        }
    }

    override fun payBooking(id: Long) {
        val booking = getBooking(id)
        if (booking.payed) {
            throw AlreadyPayedException("Booking with id=$id was already payed")
        } else {
            repo.save(
                booking.apply {
                    payed = !payed
                    session.booked--
                    session.occupancy++
                }
            )
        }
    }

    override fun getUserBookings(userId: Long, isPayed: Boolean): MutableIterable<Booking> {
        return repo.findAllByUserIdAndPayedIs(userId, isPayed)
    }

    fun evaluateTotalPrice(user: User, movieSession: MovieSession): BigDecimal? {
        return if (user.category?.discount == BigDecimal.ZERO) {
            movieSession.price
        } else {
            movieSession.price?.discount(user.category?.discount)
        }
    }


    fun BigDecimal.discount(discount: BigDecimal?): BigDecimal? {
        return this.minus(this.multiply(discount))
    }
}