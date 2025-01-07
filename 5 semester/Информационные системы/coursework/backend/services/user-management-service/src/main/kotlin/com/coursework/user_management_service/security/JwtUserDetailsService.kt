package com.coursework.user_management_service.security

import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.coursework.user_management_service.infrastructure.model.UsersEntity

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails =
        userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User $email not found") }
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User $email not found")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}