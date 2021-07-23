package ir.balonet.support_service.controller

import ir.balonet.support_service.model.entity.Admin
import ir.balonet.support_service.service.AdminService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/admin")
@RestController
class AdminController(val adminService: AdminService) {

    fun addAdminById(name: String, password: String,nationalId: Long) {//available only in console
         adminService.addAdmin(name, password, nationalId)
    }

    @DeleteMapping("/delete/byId")
    fun deleteAdminById(@RequestParam adminId: Long) { // available for admin
        adminService.deleteAdminById(adminId)
    }

    @PutMapping("/update/byId") // available for admin
    fun updateAdminById(@RequestParam adminId: Long,@RequestParam name: String,@RequestParam nationalId: Long,@RequestParam password: String): Admin {
        return adminService.updateAdminById(adminId,name,password,nationalId)
    }

    @GetMapping("/get/byId")// available for admin
    fun getAdminById(@RequestParam adminId: Long): Admin {
        return adminService.getAdminById(adminId)
    }
}

