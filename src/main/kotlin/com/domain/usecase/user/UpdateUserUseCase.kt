package com.domain.usecase.user

import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class UpdateUserUseCase (val repository : UserInterface){
    var updateUser: UpdateUser? = null
    var email: String? = null

    suspend operator fun invoke() : Boolean {
        return if (updateUser == null || email == null) {
            false
        }else{
            return repository.updateUser(updateUser!!, email!!)
        }

    }
}