package net.ddns.jaronsky.debter.security.controller

import net.ddns.jaronsky.debter.rest.service.ProtectedService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("protected")
class MethodProtectedRestController(
        private val protectedService: ProtectedService
) {

    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL expressions
     * in @PreAuthorize such as 'hasRole()' to determine if a user has access. Remember that the hasRole expression assumes a
     * 'ROLE_' prefix on all role names. So 'ADMIN' here is actually stored as 'ROLE_ADMIN' in database!
     */

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun protectedGreeting(): ResponseEntity<*> {
        return ResponseEntity.ok(protectedService.greeting())
//        return ResponseEntity.ok("Greetings from admin protected method !")
    }

//    val protectedGreeting: ResponseEntity<*>
//        @GetMapping
//        @PreAuthorize("hasRole('ROLE_ADMIN')")
//        get() = ResponseEntity.ok("Greetings from admin protected method!")

}