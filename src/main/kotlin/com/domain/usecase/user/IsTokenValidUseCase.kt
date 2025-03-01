package com.domain.usecase.user

import com.domain.repository.UserInterface

class IsTokenValidUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(username: String?, token: String?): Boolean {
        return if (username.isNullOrBlank() || token.isNullOrBlank()) {
            false
        } else {
            try {
                repository.getUserToken(username) == token
            } catch (e: Exception) {
                println("Error validando el token: ${e.localizedMessage}")
                false
            }
        }
    }
}