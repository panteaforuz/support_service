package ir.balonet.support_service.controller

import ir.balonet.support_service.model.entity.Admin
import ir.balonet.support_service.service.AdminService
import org.springframework.web.bind.annotation.*

@RequestMapping("/admin")
@RestController
class AdminController(val adminService: AdminService) {

    fun addAdmin(name: String, password: String,nationalId: Long) {//available only in console
         adminService.addAdmin(name, password, nationalId)
    }

    @DeleteMapping("/delete/byToken")
    fun deleteAdminById(@RequestParam token:String) { // available for admin
        adminService.deleteAdminByToken(token)
    }

    @PutMapping("/update/byToken") // available for admin
    fun updateAdminById(@RequestParam token:String,@RequestParam name: String,@RequestParam nationalId: Long,@RequestParam password: String): Admin {
        return adminService.updateAdminByToken(token,name,password,nationalId)
    }

    @GetMapping("/get/byToken")// available for admin
    fun getAdminByToken(@RequestParam  token:String): Admin {
        return adminService.getAdminByToken(token)
    }
    @PostMapping("/login")
    fun mediatorLogin(@RequestParam nationalId: Long, @RequestParam password: String): String {
        return adminService.login(nationalId, password)
    }
}


