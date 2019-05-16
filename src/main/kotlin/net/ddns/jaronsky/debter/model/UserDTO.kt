package net.ddns.jaronsky.debter.model

import lombok.ToString
import net.ddns.jaronsky.debter.model.security.AuthorityName

/**
 * Created by Wojciech Jaronski
 *
 */
@ToString
class UserDTO (
        val username: String,
        val email: String = "",
        val lastname: String= "",
        val firstname: String= "",
        val authorities: List<AuthorityName> = arrayListOf()
) {
    override fun toString(): String {
        return "${username} ${email} ${lastname} ${firstname} ${authorities.map { x -> x.name }.joinToString()}"
    }
}
