package ir.balonet.support_service.model.entity
import ir.balonet.support_service.model.dto.UserDtoUser
import ir.balonet.support_service.model.dto.UserDtoAdmin
import javax.persistence.*
import java.time.LocalDateTime


@Entity
data class UserModel(
    @Id
    @GeneratedValue
    var id: Long?=null,
    var name: String,
    var nationalId: Long,
    var password: String,
    @OneToMany //mappedBy??
    var tickets: MutableList<TicketModel>?=null,
    // user status
    var isLocked: Boolean = false,
//    @ElementCollection(targetClass = Authority.class, fetch=FetchType.EAGER),
   // @CollectionTable(name="authorities", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
//    @Enumerated(EnumType.STRING)
 //   var authorities: List<Authority>?
    //   @CreationTimestamp
    var createdAt: LocalDateTime,
   // var isAccountNonExpired: Boolean,
    //  var isAccountNonLocked: Boolean = ,
    //  var isCredentialsNonExpired: Boolean = false
) {
    companion object {
    fun fromUser(userDto: UserDtoUser) : UserModel {
        return UserModel(name = userDto.name,
            nationalId = userDto.nationalId,
            password = userDto.password,
            createdAt = LocalDateTime.now())
    }
        fun fromAdmin(userDto: UserDtoAdmin) : UserModel {
            return UserModel(name = userDto.name,
                nationalId = userDto.nationalId,
                password = userDto.password,
                createdAt = LocalDateTime.now())
        }
}
}
