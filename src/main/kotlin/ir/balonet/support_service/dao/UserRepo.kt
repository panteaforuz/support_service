package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo:JpaRepository<User,Long> {
    fun findByNameContaining(name:String):List<User>
    fun findByNationalId(nationalId:Long): User
    fun findByToken(token:String): User
    fun existsByToken(token:String):Boolean
    fun existsByNationalId(nationalId:Long):Boolean
    fun existsByNationalIdAndPassword(nationalId: Long,password:String):Boolean
    fun findByNationalIdAndPassword(nationalId: Long,password:String):User
}