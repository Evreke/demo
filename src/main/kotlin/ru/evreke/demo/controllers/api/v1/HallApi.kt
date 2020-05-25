package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Hall
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.HallRepository

@RestController
@RequestMapping("/api/v1/halls")
class HallApi(
    private val repo: HallRepository
) {
    @GetMapping("/", "")
    fun getAllHalls(): MutableIterable<Hall> {
        return repo.findAll()
    }

    @GetMapping("/{id}")
    fun getHall(
        @PathVariable id: Long
    ): Hall? {
        return repo.findById(id).orElseThrow { NotFoundException("Hall with id=$id not found") }
    }

    @PostMapping("/", "")
    fun createHall(
        @RequestBody hall: Hall
    ) {
        repo.save(hall)
    }

    @PutMapping("/{id}")
    fun editHall(
        @PathVariable id: Long,
        @RequestBody hall: Hall
    ) {
        repo.findById(id).orElseThrow { NotFoundException("Hall with id=$id not found") }.apply {
            hall.capacity?.let { capacity = it }
            hall.name?.let { name = it }
            repo.save(this)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteHall(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }

}