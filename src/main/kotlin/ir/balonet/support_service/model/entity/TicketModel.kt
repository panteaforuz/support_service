package ir.balonet.support_service.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
data class TicketModel(
    @Id
    @GeneratedValue
    var id: Long,
    var Massage: String,
    var response: String,
    @ManyToOne
    var user: UserModel,
    var createdAt: LocalDateTime,
    var status: TicketStatus=TicketStatus.Unseen
)
enum class TicketStatus {
    Unseen, Rejected, Answered
}
