package net.ddns.jaronsky.debter.model

import net.ddns.jaronsky.debter.model.security.AuthorityName

/**
 * Created by Wojciech Jaronski
 *
 */

class UserDTO (
        val username: String,
        val email: String = "",
        val lastname: String= "",
        val firstname: String= "",
        val authorities: List<AuthorityName> = arrayListOf()
)
