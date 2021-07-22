package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo:JpaRepository<User,Long> {
    fun findByNameContaining(name:String):List<User>
    fun findByNationalId(id:Long): User?
    fun existsByNationalId(nationalId:Long):Boolean
}