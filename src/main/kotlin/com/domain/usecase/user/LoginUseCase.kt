package com.domain.usecase.user

import com.domain.repository.UserInterface
import com.domain.security.JwtConfig

class LoginUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(name: String?, pass: String?): String? {
        return if (name.isNullOrBlank() || pass.isNullOrBlank()) {
            null
        } else {
            try {
                val isValidUser = repository.login(name, pass)
                if (isValidUser) {
                    val token = JwtConfig.generateToken(name)
                    repository.updateUserToken(name, token)
                    token
                } else {
                    null
                }
            } catch (e: Exception) {
                println("❌ Error en login: ${e.localizedMessage}")
                null
            }
        }
    }
}