package ir.balonet.support_service.service

import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {
    fun getAllUsers(): MutableList<User> {
        return userRepo.findAll()
    ///TODO List can be empty, handle it
    }

    fun getUserById(id: Long): User {
        return userRepo.findByIdOrNull(id) ?: throw NullPointerException("there is no user by this id")
        //TODO exception should change and handle
    }

    fun getByNameContaining(name: String): List<User> {
        return userRepo.findByNameContaining(name)
        ///TODO List can be empty, handle it
    }

    fun getUserByNationalId(nationalId: Long): User {
        return getUserByNationalId(nationalId)
        ///TODO User can be null, handle it
    }

    fun addUserByUser(newUser: UserDtoUser) {
        if (userRepo.existsByNationalId(newUser.nationalId))
            throw RuntimeException(" this nationalId is registered before")
            //TODO exception should change and handle
        userRepo.save(User.fromUser(newUser))
    }

    fun addUserByAdmin(newUser: UserDtoAdmin) {
        if (userRepo.existsByNationalId(newUser.nationalId))
            throw RuntimeException(" this nationalId is registered before")
            //TODO exception should change and handle
        userRepo.save(User.fromAdmin(newUser))
    }

    fun updateByUser(updatedUser: UserDtoUser, id: Long) {
        //TODO get user id from tokenize user ind dont get in request
        if (userRepo.existsById(id)) {  ///if should delete
            val exUser = userRepo.getById(id)
                exUser.name = updatedUser.name
                exUser.password = updatedUser.password
                exUser.nationalId = updatedUser.nationalId
        } else throw RuntimeException("mismatch id")
        //TODO exception should change and handle

    }

    fun updateByAdmin(updatedUser: UserDtoAdmin, id: Long) {
        if (userRepo.existsById(id)) {
            val exUser = userRepo.getById(id)
            exUser.name = updatedUser.name
            exUser.password = updatedUser.password
            exUser.nationalId = updatedUser.nationalId
            exUser.isLocked = updatedUser.isLocked
        } else throw RuntimeException("mismatch id")
        //TODO exception should change and handle
    }

    fun deleteUserById(id: Long) {
        if (userRepo.existsById(id))
            userRepo.deleteById(id)
        throw RuntimeException("mismatch id")
        //TODO exception should change and handle
    }

    fun deleteUserByNationalId(nationalId: Long) {
        if (userRepo.existsByNationalId(nationalId))
            userRepo.findByNationalId(nationalId)?.let { userRepo.delete(it) }
    }
}