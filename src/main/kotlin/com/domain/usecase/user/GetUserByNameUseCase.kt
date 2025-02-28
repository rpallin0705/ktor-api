package com.domain.usecase.user

import com.domain.models.User
import com.domain.repository.UserInterface

    class GetUserByNameUseCase (val repository : UserInterface) {
        var name : String? = null

        suspend operator fun invoke() : User? {
            return if (name?.isBlank() == true)
                null
            else{
                repository.getUserByName(name!!)
            }
        }
    }
