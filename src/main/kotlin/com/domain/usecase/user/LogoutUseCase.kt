package com.domain.usecase.user

import com.domain.repository.UserInterface

class LogoutUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(name: String?): Boolean {
        return if (name.isNullOrBlank()) {
            false
        } else {
            try {
                repository.invalidateToken(name)
                true
            } catch (e: Exception) {
                println("Error en logout: ${e.localizedMessage}")
                false
            }
        }
    }
}