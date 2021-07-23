package ir.balonet.support_service.service

import ir.balonet.support_service.dao.AdminRepo
import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.exception.AlreadyExistException
import ir.balonet.support_service.exception.ForbiddenException
import ir.balonet.support_service.exception.NotFoundException
import ir.balonet.support_service.exception.UnauthorizedException
import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.entity.User
import ir.balonet.support_service.security.SecurityHelper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo, val adminRepo: AdminRepo) {
    fun getAllUsers(token: String): MutableList<User> {
        if (adminRepo.existsByToken(token))
            return userRepo.findAll()
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getUserById(token: String, id: Long): User {
        if (adminRepo.existsByToken(token)) {
            return userRepo.findByIdOrNull(id) ?: throw NotFoundException("there is no user by $id id")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getUserByToken(token: String): User {
        if (userRepo.existsByToken(token)) {
            return userRepo.findByToken(token)
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getByNameContaining(token: String, name: String): List<User> {
        if (adminRepo.existsByToken(token))
            return userRepo.findByNameContaining(name)
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun addUserByUser(newUser: UserDtoUser) {
        if (userRepo.existsByNationalId(newUser.nationalId))
            throw AlreadyExistException(" this nationalId is registered before")
        userRepo.save(User.fromUser(newUser))
    }

    fun addUserByAdmin(token: String, newUser: UserDtoAdmin) {
        if (adminRepo.existsByToken(token)) {
            if (userRepo.existsByNationalId(newUser.nationalId))
                throw AlreadyExistException(" this nationalId is registered before")
            userRepo.save(User.fromAdmin(newUser))
        }

    }

    fun updateByUser(token: String, updatedUser: UserDtoUser): User {
        if (userRepo.existsByToken(token)) {
            val exUser = userRepo.findByToken(token).apply {
                name = updatedUser.name
                password = updatedUser.password
                nationalId = updatedUser.nationalId
            }
            return userRepo.save(exUser)
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun updateByAdmin(token: String, updatedUser: UserDtoAdmin, id: Long): User {
        if (userRepo.existsByToken(token)) {
            if (userRepo.existsById(id)) {
                val exUser = userRepo.getById(id).apply {
                    name = updatedUser.name
                    password = updatedUser.password
                    nationalId = updatedUser.nationalId
                    isLocked = updatedUser.isLocked
                }
                return userRepo.save(exUser)
            } else throw NotFoundException("there is no user by $id id")
        } else throw ForbiddenException(" don't have permission to access the requested")

    }

    fun deleteUserById(token: String, id: Long) {
        if (userRepo.existsByToken(token)) {
            if (userRepo.existsById(id))
                userRepo.deleteById(id)
            else
                throw NotFoundException("there is no user by $id id")
        } else throw ForbiddenException(" don't have permission to access the requested")

    }

    fun login(nationalId: Long, password: String): String {
        if (userRepo.existsByNationalIdAndPassword(nationalId, password)) {
            val generatedUser: User = userRepo.findByNationalIdAndPassword(nationalId, password).apply {
                token = SecurityHelper.tokenGenerator()
            }
            return userRepo.save(generatedUser).token
        } else throw UnauthorizedException("wrong user or password")
    }

}