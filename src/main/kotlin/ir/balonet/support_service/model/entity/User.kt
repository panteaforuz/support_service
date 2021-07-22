package ir.balonet.support_service.model.entity

import ir.balonet.support_service.model.dto.UserDtoAdmin
import ir.balonet.support_service.model.dto.UserDtoUser
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
data class User(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    var nationalId: Long,
    var password: String,
    @OneToMany //mappedBy??
    var tickets: MutableList<Ticket>? = mutableListOf(),
    var isLocked: Boolean = false,
    var createdAt: LocalDateTime,
) {
    companion object {
        fun fromUser(userDto: UserDtoUser): User {
            return User(
                name = userDto.name,
                nationalId = userDto.nationalId,
                password = userDto.password,
                createdAt = LocalDateTime.now()
            )
        }

        fun fromAdmin(userDto: UserDtoAdmin): User {
            return User(
                name = userDto.name,
                nationalId = userDto.nationalId,
                password = userDto.password,
                createdAt = LocalDateTime.now(),
                isLocked = userDto.isLocked
            )
        }
    }
}
