package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.MediatorDto
import ir.balonet.support_service.model.entity.Mediator
import ir.balonet.support_service.service.MediatorService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/mediator")
@RestController
class MediatorController(val mediatorService: MediatorService) {

    @PostMapping("/register", consumes = [MediaType.APPLICATION_JSON_VALUE]) // only available for admin
    fun addMedBy(@RequestBody mediatorDto: MediatorDto): Mediator {
        return mediatorService.addMed(mediatorDto)
    }

    @DeleteMapping("/delete/byMedId")
    fun deleteMedById(@RequestParam medId: Long) { // available for admin
        mediatorService.deleteMedById(medId)
    }

    @PutMapping("/update/byMedId", consumes = [MediaType.APPLICATION_JSON_VALUE]) // available for admin and mediator
    fun updateMedById(@RequestParam medId: Long,@RequestBody mediatorDto: MediatorDto): Mediator {
        return mediatorService.updateMedById(medId, mediatorDto)
    }

    @GetMapping("/get/byMedId")// available for admin and mediator
    fun getMedById(@RequestParam medId: Long): Mediator {
        return mediatorService.getMedById(medId)
    }
}
