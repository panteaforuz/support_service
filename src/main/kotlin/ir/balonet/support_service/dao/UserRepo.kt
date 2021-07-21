package ir.balonet.support_service.dao

import ir.balonet.support_service.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo:JpaRepository<UserModel,Long> {
    fun findByNameContaining(name:String):List<UserModel>
    fun findByNationalId(id:Long):UserModel
    fun existByNationalId(nationalId:Long):Boolean
}