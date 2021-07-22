package ir.balonet.support_service.controller

import ir.balonet.support_service.model.entity.TicketModel
import ir.balonet.support_service.model.entity.TicketStatus
import ir.balonet.support_service.service.TicketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ticket")
class TicketController(var ticketService: TicketService) {

    /*  @PostMapping("/postByUser") //available only for user
      fun addTicket(massage: String) {
          ///need to change :add ticket to user
          // TODO check if user is locked cant create or update ticket
          ticketService.addTicket(massage)
      }*/

    @GetMapping("/all")
    fun getAllTickets(): List<TicketModel> { //available admin and mediator
        return ticketService.getAllTickets()
    }

    @GetMapping("/getById")  //available for admin and mediator
    fun getTicketById(@RequestParam id: Long): TicketModel {
        return ticketService.getTicketById(id)
        //TODO if role is admin or mediator change the ticket status to VIEWED
    }

    @GetMapping("/getUsersTickets")  //available for all
    fun getUsersTickets(@RequestParam UserId: Long): List<TicketModel>? {
        return ticketService.getUsersTickets(UserId)
        //TODO if role is admin or mediator change the ticket status to VIEWED
    }

    @GetMapping("/getUnSeen/ByAdmin&Med")
    fun getUnSeenTickets(): List<TicketModel> {
        return ticketService.getUnSeenTickets()
    }

    @GetMapping("/getRejected/ByAdmin&Med")
    fun getRejectedTickets(): List<TicketModel> {
        return ticketService.getRejectedTickets()
    }

    @PutMapping("/update/ByUser") //available only for user
    // TODO check if user is locked cant create or update ticket
    fun updateTicketByUser(@RequestParam id: Long, @RequestParam massage: String) {
        ticketService.updateTicketByUser(id, massage)
    }

    @PutMapping("/update/ByAdmin&Med") //available for user and admin and mediator
    fun updateTicketByAdminAndMed(
        @RequestParam id: Long,
        @RequestParam response: String,
        @RequestParam status: TicketStatus
    ) {
        ticketService.updateTicketByAdminAndMed(id, response, status)
    }

}