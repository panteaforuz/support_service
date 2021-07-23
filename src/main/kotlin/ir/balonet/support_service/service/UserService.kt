package ir.balonet.support_service.service

import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.exception.AlreadyExistException
import ir.balonet.support_service.exception.NotFoundException
import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {
    fun getAllUsers(): MutableList<User> {
        return userRepo.findAll()
    }

    fun getUserById(id: Long): User {
        return userRepo.findByIdOrNull(id) ?: throw NotFoundException("there is no user by $id id")
    }

    fun getByNameContaining(name: String): List<User> {
        return userRepo.findByNameContaining(name)
    }

    fun getUserByNationalId(nationalId: Long): User {
        if (userRepo.existsByNationalId(nationalId))
            return getUserByNationalId(nationalId)
        else throw NotFoundException("user by nationalId:$nationalId not found")
    }

    fun addUserByUser(newUser: UserDtoUser) {
        if (userRepo.existsByNationalId(newUser.nationalId))
            throw AlreadyExistException(" this nationalId is registered before")
        userRepo.save(User.fromUser(newUser))
    }

    fun addUserByAdmin(newUser: UserDtoAdmin) {
        if (userRepo.existsByNationalId(newUser.nationalId))
            throw AlreadyExistException(" this nationalId is registered before")
        userRepo.save(User.fromAdmin(newUser))
    }

    fun updateByUser(updatedUser: UserDtoUser, id: Long): User {
        //TODO get user id from tokenize user ind dont get in request
        if (userRepo.existsById(id)) {
            val exUser = userRepo.getById(id).apply {
                name = updatedUser.name
                password = updatedUser.password
                nationalId = updatedUser.nationalId
            }
            userRepo.save(exUser)
            return exUser
        } else throw NotFoundException("there is no user by $id id")//


    }

    fun updateByAdmin(updatedUser: UserDtoAdmin, id: Long): User {
        if (userRepo.existsById(id)) {
            val exUser = userRepo.getById(id).apply {
                name = updatedUser.name
                password = updatedUser.password
                nationalId = updatedUser.nationalId
                isLocked = updatedUser.isLocked
            }
            return userRepo.save(exUser)
        } else throw NotFoundException("there is no user by $id id")

    }

    fun deleteUserById(id: Long) {
        if (userRepo.existsById(id))
            userRepo.deleteById(id)
        else
            throw NotFoundException("there is no user by $id id")

    }

}