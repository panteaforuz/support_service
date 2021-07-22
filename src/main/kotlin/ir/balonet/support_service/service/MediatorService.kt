package ir.balonet.support_service.service

import ir.balonet.support_service.dao.MediatorRepo
import ir.balonet.support_service.model.dto.MediatorDto
import ir.balonet.support_service.model.entity.Mediator
import javassist.NotFoundException
import org.springframework.stereotype.Service


@Service
class MediatorService(val mediatorRepo: MediatorRepo) {
    fun addMedById(mediatorDto: MediatorDto): Mediator {
        return mediatorRepo.save(Mediator.form(mediatorDto))
    }

    fun deleteMedById(medId: Long) {
        if (mediatorRepo.existsById(medId))
            mediatorRepo.deleteById(medId)
        else throw NotFoundException("mediator by id:$medId not found")
    }

    fun updateMedById(medId: Long, mediatorDto: MediatorDto): Mediator {
        if (mediatorRepo.existsById(medId)) {
            val exMed = mediatorRepo.getById(medId).apply {
                name = mediatorDto.name
                password = mediatorDto.password
                nationalId = mediatorDto.nationalId
            }
            mediatorRepo.save(exMed)
            return exMed
        } else throw NotFoundException("mediator by id:$medId not found")
    }

    fun getMedById(medId: Long): Mediator {
        if (mediatorRepo.existsById(medId))
            return mediatorRepo.getById(medId)
        else throw NotFoundException("mediator by id:$medId not found")
    }
}