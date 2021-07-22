package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import ir.balonet.support_service.service.UserService
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController("/api/user")
class UserController(var userService: UserService) {

    @GetMapping("/all") // available for admin
    fun getAllUser(): List<User> {
        return userService.getAllUsers()
    }

    @GetMapping("/get/byId")  // available for admin and user
    fun getUserById(@RequestParam id: Long): User {
        return userService.getUserById(id)
    }

    @GetMapping("/get/byName")
    fun getUsersByNameContaining(@RequestParam name: String): List<User> {  // available for admin
        return userService.getByNameContaining(name)
    }

    @GetMapping("/get/byNationalId")// available for admin and user
    fun getUserByNationalId(@RequestParam nationalId: Long): User {
        return userService.getUserByNationalId(nationalId)
    }

    @PostMapping("register/byUser") // available for user
    fun addUser(@RequestBody newUser: UserDtoUser) {
        userService.addUserByUser(newUser)
    }

    @PostMapping("register/byAdmin") // available for admin
    fun addUser(@RequestBody newUser: UserDtoAdmin) {
        userService.addUserByAdmin(newUser)
    }

    @PutMapping("/update/byUser") // available for user
    fun updateByUser(@RequestBody newUser: UserDtoUser, @RequestParam id: Long) {
        userService.updateByUser(newUser, id)
    }

    @PutMapping("/update/byAdmin") // available for admin
    fun updateByAdmin(@RequestBody newUser: UserDtoAdmin, @RequestParam id: Long) {
        userService.updateByAdmin(newUser, id)
    }

    @DeleteMapping("/del/ById") // available for admin
    fun deleteUserById(@RequestParam id: Long) {
        userService.deleteUserById(id)
    }

    @DeleteMapping("/del/ByNationalId") // available for admin and user
    fun deleteUserByNationalId(@RequestParam nationalId: Long) {
        userService.deleteUserByNationalId(nationalId)
    }

}

