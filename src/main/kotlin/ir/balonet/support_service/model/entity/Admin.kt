package ir.balonet.support_service.model.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Admin(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    var nationalId: Long,
    var password: String,
    var registeredAt: LocalDateTime,
)