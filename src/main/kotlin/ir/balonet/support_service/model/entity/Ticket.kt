package ir.balonet.support_service.model.entity

import java.time.LocalDateTime
import javax.persistence.*


@Entity
data class Ticket(
    @Id
    @GeneratedValue
    var id: Long=0,
    var massage: String,
    var response: String="",
    @ManyToOne
    var user: User,
    var createdAt: LocalDateTime,
    var status: TicketStatus = TicketStatus.UNSEEN
)
enum class TicketStatus {
    UNSEEN,VIEWED, REJECTED, ANSWERED
}
