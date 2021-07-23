package ir.balonet.support_service.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Admin(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    @JsonIgnore
    var token: String="-1",
    var nationalId: Long,
    @JsonIgnore
    var password: String,
    var registeredAt: LocalDateTime,
)