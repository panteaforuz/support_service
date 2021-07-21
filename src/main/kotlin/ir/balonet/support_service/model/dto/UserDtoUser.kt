package ir.balonet.support_service.model.dto

import lombok.Data
import javax.persistence.Entity
@Data
@Entity
class UserDtoUser(
var name: String,
var nationalId: Long,
var password: String,
)