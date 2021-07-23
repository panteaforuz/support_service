package ir.balonet.support_service.controller

import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import ir.balonet.support_service.service.UserService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*

@RequestMapping("/user")
@RestController
class UserController(var userService: UserService) {

    @GetMapping("/adminGet/all") // accessible for admin
    fun getAllUser(@RequestParam token: String): List<User> {
        return userService.getAllUsers(token)
    }

    @GetMapping("/getById/byAdmin")  // accessible for admin
    fun adminGetUserById(@RequestParam token: String, @RequestParam userId: Long): User {
        return userService.getUserById(token, userId)
    }

    @GetMapping("/get/ByToken")  // user Get his account by token
    fun userGetUserByToken(@RequestParam token: String): User {
        return userService.getUserByToken(token)
    }

    @GetMapping("/getByName/byAdmin")    // accessible for admin
    fun getUsersByNameContaining(
        @RequestParam token: String,
        @RequestParam name: String
    ): List<User> {
        return userService.getByNameContaining(token, name)
    }

    @PostMapping("/register", consumes = [APPLICATION_JSON_VALUE]) // accessible for user
    fun addUser(@RequestBody newUser: UserDtoUser) {
        userService.addUserByUser(newUser)
    }

    @PostMapping("/register/byAdmin", consumes = [APPLICATION_JSON_VALUE]) // accessible for admin
    fun addUserByAdmin(@RequestParam token: String, @RequestBody newUser: UserDtoAdmin) {
        userService.addUserByAdmin(token, newUser)
    }

    @PutMapping("/update/byUser", consumes = [APPLICATION_JSON_VALUE]) // accessible for user
    fun updateByUser(@RequestParam token: String, @RequestBody newUser: UserDtoUser): User {
        return userService.updateByUser(token, newUser)
    }

    @PutMapping("/Update/byAdmin", consumes = [APPLICATION_JSON_VALUE]) // accessible for admin
    fun updateUserByAdmin(
        @RequestParam token: String,
        @RequestBody newUser: UserDtoAdmin,
        @RequestParam id: Long
    ): User {
        return userService.updateByAdmin(token, newUser, id)/////done
    }

    @DeleteMapping("/Del/ById/byAdmin") // accessible for admin
    fun adminDeleteUserById(@RequestParam token: String, @RequestParam id: Long) {
        userService.deleteUserById(token, id)
    }

    @PostMapping("/login")
    fun userLogin(@RequestParam nationalId: Long, @RequestParam password: String): String {
        return userService.login(nationalId, password)
    }
}

