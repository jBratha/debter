package net.ddns.jaronsky.debter.rest.service

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

/**
 * Created by Wojciech Jaronski
 *
 */
@Service
class ProtectedService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun greeting(): String {
        return "Hello from protedted";
    }

}