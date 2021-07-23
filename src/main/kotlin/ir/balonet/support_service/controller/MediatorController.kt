package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.MediatorDto
import ir.balonet.support_service.model.entity.Mediator
import ir.balonet.support_service.service.MediatorService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/mediator")
@RestController
class MediatorController(val mediatorService: MediatorService) {

    @PostMapping("/register", consumes = [MediaType.APPLICATION_JSON_VALUE]) // only accessible for admin
    fun addMedBy(@RequestParam token:String ,@RequestBody mediatorDto: MediatorDto) {
         mediatorService.addMed(token,mediatorDto)
    }

    @DeleteMapping("/delete/byMedId")// accessible for admin
    fun deleteMedById(@RequestParam token:String ,@RequestParam medId: Long) {
        mediatorService.deleteMedById(token,medId)
    }

    @PutMapping("/update/byMedId/byAdmin", consumes = [MediaType.APPLICATION_JSON_VALUE]) // accessible for admin
    fun updateMedById(@RequestParam token:String ,@RequestBody medId:Long,@RequestBody mediatorDto: MediatorDto): Mediator {
        return mediatorService.updateMedById(token,medId, mediatorDto)
    }
    @PutMapping("/update/byToken", consumes = [MediaType.APPLICATION_JSON_VALUE]) // accessible for mediator
    fun updateMedByToken(@RequestParam token:String ,@RequestBody mediatorDto: MediatorDto): Mediator {
        return mediatorService.updateMedByToken(token, mediatorDto)
    }

    @GetMapping("/get/byMedToken")// accessible for mediator
    fun getMedByToken(@RequestParam token:String ): Mediator {
        return mediatorService.getMedByToken(token)
    }
    @GetMapping("/get/byAdmin/byMedId")// accessible for admin
    fun getMedById(@RequestParam token:String ,@RequestParam medId: Long): Mediator {
        return mediatorService.getMedById(token,medId)
    }
    @PostMapping("/login")
    fun mediatorLogin(@RequestParam nationalId: Long, @RequestParam password: String): String {
        return mediatorService.login(nationalId, password)
    }
}
