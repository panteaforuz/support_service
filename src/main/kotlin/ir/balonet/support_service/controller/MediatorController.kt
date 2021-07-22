package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.MediatorDto
import ir.balonet.support_service.model.entity.Mediator
import ir.balonet.support_service.service.MediatorService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/mediator")
@RestController
class MediatorController(val mediatorService: MediatorService) {

    @PostMapping("register/byMedId", consumes = [MediaType.APPLICATION_JSON_VALUE]) // available for admin
    fun addMedByMedId(mediatorDto: MediatorDto): Mediator {
        return mediatorService.addMedById(mediatorDto)
    }

    @DeleteMapping("delete/byMedId")
    fun deleteMedByAdmin(medId: Long) { // available for admin
        mediatorService.deleteMedById(medId)
    }

    @PutMapping("update/byMedId", consumes = [MediaType.APPLICATION_JSON_VALUE]) // available for admin and mediator
    fun updateMedById(medId: Long, mediatorDto: MediatorDto): Mediator {
        return mediatorService.updateMedById(medId, mediatorDto)
    }

    @GetMapping("get/byMedId")// available for admin and mediator
    fun getMedById(medId: Long): Mediator {
        return mediatorService.getMedById(medId)
    }
}
