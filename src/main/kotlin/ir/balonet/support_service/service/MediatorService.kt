package ir.balonet.support_service.service

import ir.balonet.support_service.dao.AdminRepo
import ir.balonet.support_service.dao.MediatorRepo
import ir.balonet.support_service.exception.ForbiddenException
import ir.balonet.support_service.exception.UnauthorizedException
import ir.balonet.support_service.model.dto.MediatorDto
import ir.balonet.support_service.model.entity.Mediator
import ir.balonet.support_service.security.SecurityHelper
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class MediatorService(val mediatorRepo: MediatorRepo, val adminRepo: AdminRepo) {
    fun addMed(token: String, mediatorDto: MediatorDto) {
        if (adminRepo.existsByToken(token))
            mediatorRepo.save(Mediator.form(mediatorDto))
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun deleteMedById(token: String, medId: Long) {
        if (adminRepo.existsByToken(token)) {
            if (mediatorRepo.existsById(medId))
                mediatorRepo.deleteById(medId)
            else throw NotFoundException("mediator by id:$medId not found")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun updateMedById(token: String,medId: Long, mediatorDto: MediatorDto): Mediator {
        if (adminRepo.existsByToken(token)) {
            val exMed = mediatorRepo.findByIdOrNull(medId)?:throw NotFoundException("mediator by id:$medId not found")
            exMed.apply {
                name = mediatorDto.name
                password = mediatorDto.password
                nationalId = mediatorDto.nationalId
            }
            return mediatorRepo.save(exMed)

        } else throw ForbiddenException(" don't have permission to access the requested")
    }
    fun updateMedByToken(token: String, mediatorDto: MediatorDto): Mediator {
        if (mediatorRepo.existsByToken(token)) {
            val exMed = mediatorRepo.findByToken(token).apply {
                name = mediatorDto.name
                password = mediatorDto.password
                nationalId = mediatorDto.nationalId
            }
            return mediatorRepo.save(exMed)

        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getMedByToken(token: String): Mediator {
        if (mediatorRepo.existsByToken(token)) {
            return mediatorRepo.findByToken(token)
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getMedById(token: String, medId: Long): Mediator {
        if (adminRepo.existsByToken(token)) {
            if (mediatorRepo.existsById(medId))
                return mediatorRepo.getById(medId)
            else throw NotFoundException("mediator by id:$medId not found")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun login(nationalId: Long, password: String): String {
        if (mediatorRepo.existsByNationalIdAndPassword(nationalId, password)) {
            val generatedUser: Mediator = mediatorRepo.findByNationalIdAndPassword(nationalId, password).apply {
                token = SecurityHelper.tokenGenerator()
            }
            return mediatorRepo.save(generatedUser).token
        } else throw UnauthorizedException("wrong user or password")
    }
}