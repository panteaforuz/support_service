package ir.balonet.support_service.controller

import ir.balonet.support_service.model.entity.Ticket
import ir.balonet.support_service.model.entity.TicketStatus
import ir.balonet.support_service.service.TicketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ticket")
class TicketController(var ticketService: TicketService) {

      @PostMapping("/postByUser") //available only for user
      fun addTicket(@RequestParam userId:Long,@RequestParam massage: String) {
          ticketService.addTicket(userId,massage)
          // TODO check if user is locked cant create or update ticket
      }

    @GetMapping("/all")
    fun getAllTickets(): List<Ticket> { //available admin and mediator
        return ticketService.getAllTickets()
    }

    @GetMapping("/getById")  //available for admin and mediator
    fun getTicketById(@RequestParam id: Long): Ticket {
        return ticketService.getTicketById(id)
        //TODO if role is admin or mediator change the ticket status to VIEWED
    }

    @GetMapping("/getUsersTickets")  //available for all
    fun getUsersTickets(@RequestParam UserId: Long): List<Ticket>? {
        return ticketService.getUsersTickets(UserId)
        //TODO if role is admin or mediator change the ticket status to VIEWED
    }

    @GetMapping("/getUnSeen/ByAdmin&Med")
    fun getUnSeenTickets(): List<Ticket> {
        return ticketService.getUnSeenTickets()
    }

    @GetMapping("/getRejected/ByAdmin&Med")
    fun getRejectedTickets(): List<Ticket> {
        return ticketService.getRejectedTickets()
    }

    @PutMapping("/update/ByUser") //available only for user
    // TODO check if user is locked cant create or update ticket
    fun updateTicketByUser(@RequestParam ticketId: Long, @RequestParam massage: String) {
        ticketService.updateTicketByUser(ticketId, massage)
    }

    @PutMapping("/update/ByAdmin&Med") //available for user and admin and mediator
    fun updateTicketByAdminAndMed(
        @RequestParam id: Long,
        @RequestParam response: String,
        @RequestParam status: TicketStatus
    ) {
        ticketService.updateTicketByAdminAndMed(id, response, status)
    }
    @DeleteMapping("/delete/byUserId")
    fun deleteTicketById(@RequestParam ticketId: Long){
        ticketService.deleteTicketById(ticketId)
    }

}