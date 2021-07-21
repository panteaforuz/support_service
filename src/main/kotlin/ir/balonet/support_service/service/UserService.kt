package ir.balonet.support_service.service

import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.model.UserModel
import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {
    fun getAllUsers(): MutableList<UserModel> {
        return userRepo.findAll()
    }

    fun getUserById(id: Long): UserModel {
        return userRepo.findByIdOrNull(id) ?: throw NullPointerException("there is no user by this id")
    }

    fun getByNameContaining(name: String): List<UserModel> {
        return userRepo.findByNameContaining(name)
    }

    fun getUserByNationalId(nationalId: Long): UserModel {
        return getUserByNationalId(nationalId) ?: throw NullPointerException("there is no user by this nationalId")
    }

    fun addUserByUser(newUser: UserDtoUser) {
        if (userRepo.existByNationalId(newUser.nationalId))
            throw RuntimeException(" this nationalId is registered before")
        userRepo.save(UserModel.fromUser(newUser))
    }

    fun addUserByAdmin(newUser: UserDtoAdmin) {
        if (userRepo.existByNationalId(newUser.nationalId))
            throw RuntimeException(" this nationalId is registered before")
        userRepo.save(UserModel.fromAdmin(newUser))
    }

    fun updateByUser(updatedUser: UserDtoUser, id: Long) {
        if (userRepo.existsById(id)) {
            val exUser = userRepo.getById(id)
            exUser.name = updatedUser.name
            exUser.password = updatedUser.password
            exUser.nationalId = updatedUser.nationalId
        } else throw RuntimeException("mismatch id")

    }

    fun updateByAdmin(updatedUser: UserDtoAdmin, id: Long) {
        if (userRepo.existsById(id)) {
            val exUser = userRepo.getById(id)
            exUser.name = updatedUser.name
            exUser.password = updatedUser.password
            exUser.nationalId = updatedUser.nationalId
            exUser.isLocked = updatedUser.isLocke
        } else throw RuntimeException("mismatch id")

    }

    fun deleteUserById(id: Long) {
        if (userRepo.existsById(id))
            userRepo.deleteById(id)
        throw RuntimeException("mismatch id")
    }

    fun deleteUserByNationalId(nationalId: Long) {
        if (userRepo.existByNationalId(nationalId))
            userRepo.delete(userRepo.findByNationalId(nationalId))
    }
}