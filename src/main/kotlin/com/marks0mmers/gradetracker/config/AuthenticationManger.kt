package com.marks0mmers.gradetracker.config

import com.marks0mmers.gradetracker.models.constants.Role
import com.marks0mmers.gradetracker.util.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
@Component
class AuthenticationManager @Autowired constructor(private val jwtUtil: JWTUtil) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val authToken = authentication?.credentials.toString()
        val username = try {
            jwtUtil.getUsernameFromToken(authToken)
        } catch (e: Exception) {
            null
        }
        return if (username != null && jwtUtil.validateToken(authToken)) {
            val claims = jwtUtil.getAllClaimsFromToken(authToken)
            val rolesMap: List<String> = claims["role"] as List<String>
            val roles = rolesMap.map { Role.valueOf(it) }
            val auth = UsernamePasswordAuthenticationToken(username, null, roles.map { SimpleGrantedAuthority(it.name)})
            Mono.just(auth)
        } else {
            Mono.empty()
        }
    }
}
