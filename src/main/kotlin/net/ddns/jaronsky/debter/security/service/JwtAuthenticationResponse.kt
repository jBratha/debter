package net.ddns.jaronsky.debter.security.service

import net.ddns.jaronsky.debter.model.security.AuthorityName

class JwtAuthenticationResponse(
        val token: String,
        val authorities: List<AuthorityName?> = arrayListOf()
)
