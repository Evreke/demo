package ru.evreke.demo.services

import org.springframework.stereotype.Service
import ru.evreke.demo.entity.Hall
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.services.interfaces.HallService
import ru.evreke.demo.repository.HallRepository

@Service
class HallServiceImpl(
    private val repo: HallRepository
) : HallService {
    override fun createHall(hall: Hall) {
        repo.save(hall)
    }

    override fun getHall(id: Long): Hall {
        return repo.findById(id).orElseThrow { NotFoundException("Hall with id=$id was not found") }
    }

    override fun getAllHalls(): MutableIterable<Hall> {
        return repo.findAll()
    }

    override fun deleteHall(id: Long) {
        repo.deleteById(id)
    }

    override fun updateHall(id: Long, hall: Hall) {
        repo.save(
            getHall(id).apply {
                hall.capacity?.let { capacity = it }
                hall.name?.let { name = it }
            }
        )
    }
}