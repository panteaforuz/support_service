package ir.balonet.support_service.model.entity

import ir.balonet.support_service.model.dto.MediatorDto
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Mediator(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    var nationalId: Long,
    var password: String,
    var registeredAt: LocalDateTime,
) {
    companion object {
        fun form(mediatorDto: MediatorDto): Mediator {
            return Mediator(
                name = mediatorDto.name,
                nationalId = mediatorDto.nationalId,
                password = mediatorDto.password,
                registeredAt = LocalDateTime.now()

            )
        }
    }
}