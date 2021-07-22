package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import ir.balonet.support_service.service.UserService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/user")
@RestController
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

    @PostMapping("register/byUser" ,consumes = [APPLICATION_JSON_VALUE]) // available for user
    fun addUser(@RequestBody newUser: UserDtoUser) {
        userService.addUserByUser(newUser)
    }

    @PostMapping("register/byAdmin",consumes = [APPLICATION_JSON_VALUE]) // available for admin
    fun addUser(@RequestBody newUser: UserDtoAdmin) {
        userService.addUserByAdmin(newUser)
    }

    @PutMapping("/update/byUser",consumes = [APPLICATION_JSON_VALUE]) // available for user
    fun updateByUser(@RequestBody newUser: UserDtoUser, @RequestParam id: Long):User {
        return userService.updateByUser(newUser, id)
    }

    @PutMapping("/update/byAdmin",consumes = [APPLICATION_JSON_VALUE]) // available for admin
    fun updateByAdmin(@RequestBody newUser: UserDtoAdmin, @RequestParam id: Long):User {
        return userService.updateByAdmin(newUser, id)
    }

    @DeleteMapping("/del/ById") // available for admin
    fun deleteUserById(@RequestParam id: Long) {
        userService.deleteUserById(id)
    }


}

