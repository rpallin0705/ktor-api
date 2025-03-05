package com.domain.usecase.user

import com.domain.models.User
import com.domain.repository.UserInterface

    class GetUserByNameUseCase (val repository : UserInterface) {
        var email : String? = null

        suspend operator fun invoke() : User? {
            return if (email?.isBlank() == true)
                null
            else{
                repository.getUserByEmail(email!!)
            }
        }
    }
