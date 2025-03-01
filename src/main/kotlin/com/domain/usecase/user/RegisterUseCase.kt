package com.domain.usecase.user

import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class RegisterUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(user: UpdateUser): Boolean {
        user.username = user.username ?: return false
        user.password = user.password ?: return false

        return if (repository.getUserByName(user.username!!) != null)
            false
        else {
            val registeredUser = repository.register(user)
            registeredUser != null
        }
    }
}
