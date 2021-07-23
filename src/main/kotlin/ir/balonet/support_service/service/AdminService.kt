package ir.balonet.support_service.service

import ir.balonet.support_service.dao.AdminRepo
import ir.balonet.support_service.model.entity.Admin
import javassist.NotFoundException
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

    fun updateAdminById(adminId: Long, newName: String, newPassword: String, newNationalId: Long): Admin {
        if (adminRepo.existsById(adminId)) {
            val exAdmin = adminRepo.getById(adminId).apply {
                name = newName
                password = newPassword
                nationalId = newNationalId

            }
            return adminRepo.save(exAdmin)
        } else throw NotFoundException("admin by id:$adminId not found")
    }

    fun deleteAdminById(adminId: Long) {
        if (adminRepo.existsById(adminId))
            adminRepo.deleteById(adminId)
        else throw NotFoundException("admin by id:$adminId not found")
    }

    fun getAdminById(adminId: Long): Admin {
        if (adminRepo.existsById(adminId))
            return adminRepo.getById(adminId)
        else throw NotFoundException("admin by id:$adminId not found")
    }
}