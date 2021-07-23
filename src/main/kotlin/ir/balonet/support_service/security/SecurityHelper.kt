package ir.balonet.support_service.security

import org.apache.commons.lang3.RandomStringUtils

class SecurityHelper {
    companion object {
        fun tokenGenerator(): String {
            return RandomStringUtils.randomAlphanumeric(20)
        }
    }
}