package ir.balonet.support_service.service

import ir.balonet.support_service.dao.AdminRepo
import ir.balonet.support_service.dao.MediatorRepo
import ir.balonet.support_service.dao.TicketRepo
import ir.balonet.support_service.dao.UserRepo
import ir.balonet.support_service.exception.ForbiddenException
import ir.balonet.support_service.exception.NotFoundException
import ir.balonet.support_service.exception.PreconditionException
import ir.balonet.support_service.model.entity.Ticket
import ir.balonet.support_service.model.entity.TicketStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketService(
    val ticketRepo: TicketRepo,
    val userRepo: UserRepo,
    val mediatorRepo: MediatorRepo,
    val adminRepo: AdminRepo
) {

    fun addTicket(token: String, msg: String) {
        if (userRepo.existsByToken(token)) {
            if (userRepo.findByToken(token).isLocked)
                throw PreconditionException("your account has locked by admin!")
            else {
                val newTicket = Ticket(
                    massage = msg,
                    user = userRepo.findByToken(token),
                    createdAt = LocalDateTime.now()
                )
                ticketRepo.save(newTicket)
            }
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getAllTickets(token: String): MutableList<Ticket> {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token))
            return ticketRepo.findAll()
        else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getTicketById(token: String, id: Long): Ticket {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token)) {
            if (ticketRepo.existsById(id)) {
                return ticketRepo.save(ticketRepo.getById(id).apply {
                    status = TicketStatus.VIEWED    //change the ticket status to VIEWED
                }
                )
            } else throw NotFoundException("ticket by id:$id  not found")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getUsersTicketsByToken(token: String): MutableList<Ticket>? {
        if (userRepo.existsByToken(token)) {
            return userRepo.findByToken(token).tickets
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getUsersTicketsById(token: String, userId: Long): MutableList<Ticket>? {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token)) {
            if (userRepo.existsById(userId)) {
                val ticketList = userRepo.getById(userId).tickets
                for (t in ticketList) {
                    t.status = TicketStatus.VIEWED
                    ticketRepo.save(t)
                }
                return ticketList
            } else throw NotFoundException("there is no user by $userId id")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }


    fun getUnSeenTickets(token: String): List<Ticket> {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token)) {
            val exList = ticketRepo.findAll()
            val changeList = exList.filter { it.status == TicketStatus.UNSEEN }
            for (t in changeList) {
                t.status = TicketStatus.VIEWED
                ticketRepo.save(t)
            }
            return exList  //////////////////////////////////
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun getRejectedTickets(token: String): List<Ticket> {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token)) {
            return ticketRepo.findAll().filter { it.status == TicketStatus.REJECTED }
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun updateTicketByUser(token: String, ticketId: Long, massage: String): Ticket {
        if (userRepo.existsByToken(token)) {
            val ticket = ticketRepo.findByIdOrNull(ticketId) ?: throw NotFoundException("ticket by id:$ticketId  not found")
            if (ticket.user != userRepo.findByToken(token))
                throw ForbiddenException(" don't have permission to access the requested")
            //else
            val user = userRepo.findByToken(token)
            if (!user.isLocked) {
                ticket.massage = massage
                ticket.status = TicketStatus.UNSEEN
                return ticketRepo.save(ticket)
            } else throw PreconditionException("The user account is locked by admin")
        }else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun updateTicketByAdminAndMed(token: String, ticketId: Long, response: String, status: TicketStatus): Ticket {
        if (adminRepo.existsByToken(token) || mediatorRepo.existsByToken(token)) {
            if (ticketRepo.existsById(ticketId)) {
                val exTicket = ticketRepo.getById(ticketId)
                exTicket.response = response
                exTicket.status = status
                return ticketRepo.save(exTicket)
            } else throw NotFoundException("ticket by id:$ticketId not found")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }

    fun deleteTicketById(token: String, ticketId: Long) {
        if (userRepo.existsByToken(token)) {
            val ticket =
                ticketRepo.findByIdOrNull(ticketId) ?: throw NotFoundException("ticket by id:$ticketId  not found")
            if (ticket.user == userRepo.findByToken(token))
                ticketRepo.deleteById(ticketId)
            else throw ForbiddenException(" don't have permission to access the requested")
        } else throw ForbiddenException(" don't have permission to access the requested")
    }
}

