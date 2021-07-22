package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepo:JpaRepository<Ticket,Long> {
}