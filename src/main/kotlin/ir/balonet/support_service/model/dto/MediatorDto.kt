package ir.balonet.support_service.model.dto

import lombok.Data

@Data
class MediatorDto(
    var name: String,
    var nationalId: Long,
    var password: String,
)