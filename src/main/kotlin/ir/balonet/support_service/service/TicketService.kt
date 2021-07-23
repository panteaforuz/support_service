package ir.balonet.support_service.service

import ir.balonet.support_service.dao.TicketRepo
import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.exception.NotFoundException
import ir.balonet.support_service.model.entity.Ticket
import ir.balonet.support_service.model.entity.TicketStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketService(val ticketRepo: TicketRepo, val userRepo: UserRepo) {
    fun addTicket(userId: Long, msg: String) {
        if (userRepo.existsById(userId)) {
            val newTicket = Ticket(massage = msg, user = userRepo.getById(userId), createdAt = LocalDateTime.now())
            ticketRepo.save(newTicket)
        } else throw NotFoundException("user by $userId id not found")
    }

    fun getAllTickets():MutableList<Ticket> {
        return ticketRepo.findAll()
        ///TODO tickets can be empty, handle it
    }

    fun getTicketById(id: Long): Ticket {
        if (ticketRepo.existsById(id))
            return ticketRepo.getById(id)
        else throw NotFoundException("ticket by id:$id  not found")
    }

    fun getUsersTickets(userId: Long): MutableList<Ticket>? {
        if (userRepo.existsById(userId)) {
            val user = userRepo.getById(userId)
            return user.tickets
        } else throw NotFoundException("user by id:$userId  not found")
        ///TODO if role is admin or medator,change the tickets status to viewed
    }

    fun getUnSeenTickets(): List<Ticket> {
        val tickets = ticketRepo.findAll()
        ///TODO tickets can be empty, handle it
        return tickets.filter { it.status == TicketStatus.UNSEEN }
    }

    fun getRejectedTickets(): List<Ticket> {
        val tickets = ticketRepo.findAll()
        ///TODO tickets can be empty, handle it
        return tickets.filter { it.status == TicketStatus.REJECTED }
    }

    fun updateTicketByUser(ticketId: Long, massage: String): Ticket {
        //TODO check if the ticket belongs to user requested
        if (ticketRepo.existsById(ticketId)) {
            val exTicket = ticketRepo.getById(ticketId)
            if (exTicket.status != TicketStatus.REJECTED) {
                exTicket.massage = massage
                exTicket.status = TicketStatus.UNSEEN
                return ticketRepo.save(exTicket)
            } else throw RuntimeException("ticket has been rejected")/////////////////////TODO consider a new exception for this
        } else
            throw NotFoundException("ticket by id:$ticketId  not found")
    }

    fun updateTicketByAdminAndMed(ticketId: Long, response: String, status: TicketStatus): Ticket {
        if (ticketRepo.existsById(ticketId)) {
            val exTicket = ticketRepo.getById(ticketId)
            exTicket.response = response
            exTicket.status = status
            ticketRepo.save(exTicket)
            return exTicket
        } else
            throw NotFoundException("ticket by id:$ticketId not found")

    }

    fun deleteTicketById(ticketId: Long) {
        //TODO check if the ticketId belongs to user requested
        if (ticketRepo.existsById(ticketId))
            ticketRepo.deleteById(ticketId)
        else
            throw RuntimeException("ticket by id:$ticketId not found")
    }
}

