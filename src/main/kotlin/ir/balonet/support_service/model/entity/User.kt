package ir.balonet.support_service.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import lombok.ToString
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    var nationalId: Long,
    @JsonIgnore
    var password: String,
    @JsonIgnore
    var token: String="-1",
    @OneToMany (mappedBy="user",orphanRemoval = true )
    @JsonIgnore
    var tickets: MutableList<Ticket> = mutableListOf(),
    var isLocked: Boolean= false,
    var registeredAt: LocalDateTime,
) {
    companion object {
        fun fromUser(userDto: UserDtoUser): User {
            return User(
                name = userDto.name,
                nationalId = userDto.nationalId,
                password = userDto.password,
                registeredAt = LocalDateTime.now()
            )
        }

        fun fromAdmin(userDto: UserDtoAdmin): User {
            return User(
                name = userDto.name,
                nationalId = userDto.nationalId,
                password = userDto.password,
                registeredAt = LocalDateTime.now(),
                isLocked = userDto.isLocked
            )
        }
    }
}
