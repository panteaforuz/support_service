package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.Mediator
import org.springframework.data.jpa.repository.JpaRepository

interface MediatorRepo:JpaRepository<Mediator,Long> {
    fun findByToken(token:String): Mediator
    fun existsByToken(token:String):Boolean
    fun existsByNationalIdAndPassword(nationalId: Long,password:String):Boolean
    fun findByNationalIdAndPassword(nationalId: Long,password:String): Mediator
}
