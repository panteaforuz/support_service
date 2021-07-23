package ir.balonet.support_service.model.dto

import lombok.Data


@Data
class UserDtoAdmin(
    var name: String,
    var nationalId: Long,
    var password: String,
    var isLocked:Boolean
)