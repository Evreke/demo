package ru.evreke.demo.services

import com.github.javafaker.Faker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import ru.evreke.demo.entity.*
import ru.evreke.demo.exceptions.AlreadyPayedException
import ru.evreke.demo.exceptions.BookingException
import ru.evreke.demo.repository.BookingRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random

@SpringBootTest
@TestPropertySource("/application.yaml")
internal class BookingServiceImplTest {

    @Autowired
    private lateinit var bookingService: BookingServiceImpl

    @MockBean
    private lateinit var bookingRepo: BookingRepository
    private val faker = Faker()
    private val time: LocalDateTime = LocalDateTime.now()
    private val movie = Movie(title = faker.artist().name(), duration = LocalTime.of(1, 30))
    private val hall = Hall(name = faker.elderScrolls().firstName(), capacity = Random.nextInt(10, 200))
    private val privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers = MovieSession(startedAt = time, endedAt = time.plusMinutes(90)).also {
        it.hall = hall
        it.movie = movie
        it.privileged = true
        it.price = BigDecimal("500")
        it.startSellingAt = time.minusMinutes(90)
    }
    private val privilegedMovieSessionWithClosedSaleForNotPrivilegedUsers = MovieSession(startedAt = time, endedAt = time.plusMinutes(90)).also {
        it.hall = hall
        it.movie = movie
        it.privileged = true
        it.price = BigDecimal("500")
        it.startSellingAt = time.plusDays(1)
    }
    private val notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers = MovieSession(startedAt = time, endedAt = time.plusMinutes(90)).also {
        it.hall = hall
        it.movie = movie
        it.privileged = false
        it.price = BigDecimal("500")
        it.startSellingAt = time.plusDays(1)
    }
    private val nonPrivilegedUserCategory = Category(title = "Обычная", discount = BigDecimal.ZERO)
    private val nonPrivilegedUser = User(username = faker.name().username(), phoneNumber = faker.phoneNumber().phoneNumber()).also { it.category = nonPrivilegedUserCategory }
    private val privilegedUserCategory = Category(title = "Социальная-1", discount = BigDecimal("0.1"))
    private val privilegedUser = User(username = faker.name().username(), phoneNumber = faker.phoneNumber().phoneNumber()).also { it.category = privilegedUserCategory }

    @Test
    fun createBooking() {
        val booking = Booking(session = privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        val result = bookingService.createBooking(privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers, nonPrivilegedUser)
        Assertions.assertNotNull(result)
        Assertions.assertEquals(booking.user, result.user)
    }

    @Test
    fun createBookingForNotPrivilegedUserWithClosedSale() {
        Assertions.assertThrows(BookingException::class.java) {
            bookingService.createBooking(movieSession = privilegedMovieSessionWithClosedSaleForNotPrivilegedUsers, user = nonPrivilegedUser)
        }
    }

    @Test
    fun createBookingForPrivilegedUserWithClosedSale() {
        val booking = Booking(session = privilegedMovieSessionWithClosedSaleForNotPrivilegedUsers).also {
            it.session.booked++
            it.user = privilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(privilegedUser, privilegedMovieSessionWithClosedSaleForNotPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        Assertions.assertNotNull(bookingService.createBooking(movieSession = privilegedMovieSessionWithClosedSaleForNotPrivilegedUsers, user = privilegedUser))
    }

    @Test
    fun createBookingForNotPrivilegedUserWithClosedSaleButWithNotPrivilegedSession() {
        val booking = Booking(session = notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        val result = bookingService.createBooking(movieSession = notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers, user = nonPrivilegedUser)
        Assertions.assertEquals(booking.user, result.user)
    }

    @Test
    fun evaluateDiscountForNotPrivilegedUser() {
        val booking = Booking(session = notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, notPrivilegedMovieSessionWithClosedSaleForNotPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        val resultWithoutDiscount = bookingService.createBooking(privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers, nonPrivilegedUser)
        Assertions.assertEquals(resultWithoutDiscount.totalPrice, bookingService.evaluateTotalPrice(nonPrivilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers))
    }

    @Test
    fun evaluateDiscountForPrivilegedUser() {
        val booking = Booking(session = privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers).also {
            it.session.booked++
            it.user = privilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(privilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        val resultWithDiscount = bookingService.createBooking(privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers, privilegedUser)
        Assertions.assertEquals(resultWithDiscount.totalPrice, bookingService.evaluateTotalPrice(privilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers))
    }

    @Test
    fun deletePayedBooking() {
        val booking = Booking(session = privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        bookingService.payBooking(booking)
        Assertions.assertThrows(AlreadyPayedException::class.java) { bookingService.deleteBooking(booking, booking.user) }
    }

    @Test
    fun payBooking() {
        val booking = Booking(session = privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        bookingService.payBooking(booking)
        val isPayed = booking.payed
        Assertions.assertTrue(isPayed)
    }

    @Test
    fun payPayedBooking() {
        val booking = Booking(session = privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers).also {
            it.session.booked++
            it.user = nonPrivilegedUser
            it.totalPrice = bookingService.evaluateTotalPrice(nonPrivilegedUser, privilegedMovieSessionWithOpenedSaleForNonPrivilegedUsers)
        }
        Mockito.`when`(bookingRepo.save(Mockito.any(Booking::class.java))).thenReturn(booking)
        bookingService.payBooking(booking)
        Assertions.assertThrows(AlreadyPayedException::class.java) { bookingService.payBooking(booking) }
    }
}