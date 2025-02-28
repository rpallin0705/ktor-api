package com.domain.usecase.user

import com.domain.models.User
import com.domain.repository.UserInterface

class InsertUserUseCase  (val repository : UserInterface){

    var user : User? = null

    suspend operator fun invoke() : Boolean {
        return if (user == null) {
            false
        }else {
            repository.postUser(user!!)
        }
    }
}