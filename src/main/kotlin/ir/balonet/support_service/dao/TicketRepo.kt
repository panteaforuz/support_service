package ir.balonet.support_service.dao

import ir.balonet.support_service.model.TicketModel
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepo:JpaRepository<TicketModel,Long> {
}