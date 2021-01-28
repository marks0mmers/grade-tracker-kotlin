package com.marks0mmers.gradetracker.util

import com.marks0mmers.gradetracker.models.dto.UserDto
import com.marks0mmers.gradetracker.models.persistent.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

@Component
@ConfigurationProperties(prefix = "springbootwebfluxjjwt.jjwt")
class JWTUtil : Serializable {
    var secret: String = ""
    var expirationTime: Long = 0

    fun getAllClaimsFromToken(token: String): Claims {
        val encodedString = Base64.getEncoder().encodeToString(secret.toByteArray())
        return Jwts
            .parser()
            .setSigningKey(encodedString)
            .parseClaimsJws(token)
            .body
    }

    fun getUsernameFromToken(token: String): String = getAllClaimsFromToken(token).subject

    fun getExpirationDateFromToken(token: String): Date = getAllClaimsFromToken(token).expiration

    fun isTokenExpired(token: String) = getExpirationDateFromToken(token).before(Date())

    fun generateToken(user: User): String {
        val claims = HashMap<String, Any>()
        claims["role"] = user.roles
        return doGenerateToken(claims, user.username)
    }

    fun generateToken(user: UserDto): String {
        val claims = HashMap<String, Any>()
        claims["role"] = user.roles
        return doGenerateToken(claims, user.username)
    }

    @Suppress("DEPRECATION")
    private fun doGenerateToken(claims: Map<String, Any>, username: String): String {
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTime * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.toByteArray()))
            .compact()
    }

    fun validateToken(token: String) = !isTokenExpired(token)
}
