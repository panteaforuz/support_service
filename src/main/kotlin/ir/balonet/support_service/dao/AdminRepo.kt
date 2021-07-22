package ir.balonet.support_service.dao

import ir.balonet.support_service.model.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepo:JpaRepository<Admin,Long> {
}