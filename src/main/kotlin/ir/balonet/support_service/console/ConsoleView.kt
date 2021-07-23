package ir.balonet.support_service.console

import ir.balonet.support_service.controller.AdminController
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ConsoleView(val adminController: AdminController ) :CommandLineRunner{
    override fun run(vararg args: String?) {
        adminController.addAdmin("pantea","1234" ,127311)
    }
}