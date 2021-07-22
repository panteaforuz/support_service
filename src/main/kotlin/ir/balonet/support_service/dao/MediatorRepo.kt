package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.Mediator
import org.springframework.data.jpa.repository.JpaRepository

interface MediatorRepo:JpaRepository<Mediator,Long> {
}