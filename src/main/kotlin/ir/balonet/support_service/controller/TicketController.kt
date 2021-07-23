package ir.balonet.support_service.controller

import ir.balonet.support_service.model.entity.Ticket
import ir.balonet.support_service.model.entity.TicketStatus
import ir.balonet.support_service.service.TicketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ticket")
class TicketController(var ticketService: TicketService) {

      @PostMapping("/postByUser")   //accessible only for user
      fun addTicket(@RequestParam token:String,@RequestParam massage: String) {
          ticketService.addTicket(token,massage)
      }

    @GetMapping("/all")  //accessible admin and mediator
    fun getAllTickets(@RequestParam token:String): List<Ticket> {
        return ticketService.getAllTickets(token)
    }

    @GetMapping("/getById")  //accessible for admin and mediator
    fun getTicketById(@RequestParam token:String,@RequestParam id: Long): Ticket {
        return ticketService.getTicketById(token,id)

    }

    @GetMapping("/getUsersTickets")  //accessible for user
    fun userGetTicketsByToken(@RequestParam token:String): List<Ticket>? {
        return ticketService.getUsersTicketsByToken(token)
    }
    @GetMapping("/getUsersTickets/byAdmin&Med")  //accessible for admin and mediator
    fun adminAndMedGetTicketsByUserId(@RequestParam token:String,@RequestParam UserId: Long): List<Ticket>? {
        return ticketService.getUsersTicketsById(token,UserId)
    }

    @GetMapping("/getUnSeen/ByAdmin&Med")
    fun getUnSeenTickets(@RequestParam token:String): List<Ticket> {
        return ticketService.getUnSeenTickets(token)
    }

    @GetMapping("/getRejected/ByAdmin&Med")
    fun getRejectedTickets(@RequestParam token:String): List<Ticket> {
        return ticketService.getRejectedTickets(token)
    }

    @PutMapping("/update/ByUser") //accessible only for user
    fun updateTicketByUser(@RequestParam token:String,@RequestParam ticketId: Long, @RequestParam massage: String) {
        ticketService.updateTicketByUser(token,ticketId, massage)
    }

    @PutMapping("/update/ByAdmin&Med") //accessible for user and admin and mediator
    fun updateTicketByAdminAndMed(
        @RequestParam token:String,
        @RequestParam id: Long,
        @RequestParam response: String,
        @RequestParam status: TicketStatus
    ) {
        ticketService.updateTicketByAdminAndMed(token ,id, response, status)
    }
    @DeleteMapping("/delete/byUser")
    fun deleteTicketByUserToken(@RequestParam token:String,@RequestParam ticketId: Long){
        ticketService.deleteTicketById(token,ticketId)
    }

}