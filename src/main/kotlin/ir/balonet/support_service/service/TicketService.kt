package ir.balonet.support_service.service

import ir.balonet.support_service.dao.TicketRepo
import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.model.entity.TicketModel
import ir.balonet.support_service.model.entity.TicketStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketService(val ticketRepo: TicketRepo, val userRepo: UserRepo) {
    /*fun addTicket(massage: String) {
        ticketRepo.save(
            TicketModel(
                massage = massage,
                createdAt = LocalDateTime.now(),
                user =
                //TODO set user gets by token))

    }*/

    fun getAllTickets(): List<TicketModel> {
        return ticketRepo.findAll()
    ///TODO tickets can be empty, handle it
    }

    fun getTicketById(id: Long): TicketModel {
        if (ticketRepo.existsById(id))
            return ticketRepo.getById(id)
        else throw RuntimeException("ticket by this id not found")
    }
    fun getUsersTickets(userId:Long): MutableList<TicketModel>? {
        if (userRepo.existsById(userId)){
            val user = userRepo.getById(userId)
            return user.tickets
        }
        else throw RuntimeException("user by this id not found")
        ///TODO if role is admin or medator,change the tickets status to viewed
    }

    fun getUnSeenTickets(): List<TicketModel> {
        val tickets = ticketRepo.findAll()
        ///TODO tickets can be empty, handle it
        return tickets.filter { it.status == TicketStatus.UNSEEN }
    }

    fun getRejectedTickets(): List<TicketModel> {
        val tickets = ticketRepo.findAll()
        ///TODO tickets can be empty, handle it
        return tickets.filter { it.status == TicketStatus.REJECTED }
    }

    fun updateTicketByUser(id: Long, massage: String): TicketModel {
        //TODO check if the ticket belongs to user requested
        if (ticketRepo.existsById(id)) {
            val ticket = ticketRepo.getById(id)
            if (ticket.status!= TicketStatus.REJECTED) {
                ticket.massage = massage
                ticket.status = TicketStatus.UNSEEN
            }
            return ticket
        } else
            throw RuntimeException("ticket by this id not found")
    }

    fun updateTicketByAdminAndMed(id: Long, response: String, status: TicketStatus): TicketModel {
        if (ticketRepo.existsById(id)) {
            val ticket = ticketRepo.getById(id)
            ticket.response = response
            ticket.status = status
            return ticket
        } else
            throw RuntimeException("ticket by this id not found")

    }

}