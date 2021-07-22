package ir.balonet.support_service.model.dto

import lombok.Data

@Data
class UserDtoUser(
var name: String,
var nationalId: Long,
var password: String,
)