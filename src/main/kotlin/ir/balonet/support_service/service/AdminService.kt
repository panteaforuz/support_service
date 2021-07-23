package ir.balonet.support_service.service

import ir.balonet.support_service.dao.AdminRepo
import ir.balonet.support_service.exception.ForbiddenException
import ir.balonet.support_service.exception.UnauthorizedException
import ir.balonet.support_service.model.entity.Admin
import ir.balonet.support_service.security.SecurityHelper
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AdminService(val adminRepo: AdminRepo) {
    fun addAdmin(name: String, password: String, nationalId: Long) {
        val admin = Admin(
            name = name,
            password = password,
            nationalId = nationalId,
            registeredAt = LocalDateTime.now()
        )
        adminRepo.save(admin)
    }

    fun deleteAdminByToken(token: String) {
        if (adminRepo.existsByToken(token))
            adminRepo.delete(adminRepo.findByToken(token))
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun updateAdminByToken(token: String, newName: String, newPassword: String, newNationalId: Long): Admin {
        if (adminRepo.existsByToken(token)) {
            val exAdmin = adminRepo.findByToken(token).apply {
                name = newName
                password = newPassword
                nationalId = newNationalId
            }
            return adminRepo.save(exAdmin)
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getAdminByToken(token: String): Admin {
        if (adminRepo.existsByToken(token))
            return adminRepo.findByToken(token)
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun login(nationalId: Long, password: String): String {
        if (adminRepo.existsByNationalIdAndPassword(nationalId, password)) {
            val generatedUser: Admin = adminRepo.findByNationalIdAndPassword(nationalId, password).apply {
                token = SecurityHelper.tokenGenerator()
            }
            return adminRepo.save(generatedUser).token
        } else throw UnauthorizedException("wrong user or password")
    }
}