package com.domain.usecase.user

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class RegisterUseCase(val repository: UserInterface) {
    suspend operator fun invoke(user: UpdateUser): User? {
        user.username = user.username!!
        user.password = user.password!!
        user.token = user.token ?: ""

        return if (repository.login(user.username!!, user.password!!))
            null
        else
            repository.register(user)
    }
}