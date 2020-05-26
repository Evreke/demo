package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Hall
import ru.evreke.demo.services.interfaces.HallService

@RestController
@RequestMapping("/api/v1/halls")
class HallApi(
    private val hallService: HallService
) {
    @GetMapping("/", "")
    fun getAllHalls(): MutableIterable<Hall> {
        return hallService.getAllHalls()
    }

    @GetMapping("/{id}")
    fun getHall(
        @PathVariable id: Long
    ): Hall {
        return hallService.getHall(id)
    }

    @PostMapping("/", "")
    fun createHall(
        @RequestBody hall: Hall
    ) {
        hallService.createHall(hall)
    }

    @PutMapping("/{id}")
    fun editHall(
        @PathVariable id: Long,
        @RequestBody hall: Hall
    ) {
        hallService.updateHall(id, hall)
    }

    @DeleteMapping("/{id}")
    fun deleteHall(
        @PathVariable id: Long
    ) {
        hallService.deleteHall(id)
    }

}