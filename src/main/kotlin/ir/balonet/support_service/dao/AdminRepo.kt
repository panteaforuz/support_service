package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepo:JpaRepository<Admin,Long> {
    fun findByToken(token:String): Admin
    fun existsByToken(token:String):Boolean
    fun existsByNationalIdAndPassword(nationalId: Long,password:String):Boolean
    fun findByNationalIdAndPassword(nationalId: Long,password:String): Admin
}