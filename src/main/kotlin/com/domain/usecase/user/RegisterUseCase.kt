package com.domain.usecase.user

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class RegisterUseCase(val repository: UserInterface) {
    suspend operator fun invoke(user: UpdateUser): User? {

        user.name = user.name!!
        user.password = user.password!!
        user.token = user.token?: ""

        return if (repository.login(user.name!!, user.password!!))
                    null
                else
                    repository.register(user)
    }
}