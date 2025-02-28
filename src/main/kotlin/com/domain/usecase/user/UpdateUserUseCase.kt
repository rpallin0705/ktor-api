package com.domain.usecase.user

import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class UpdateUserUseCase (val repository : UserInterface){
    var updateUser: UpdateUser? = null
    var name: String? = null

    suspend operator fun invoke() : Boolean {
        return if (updateUser == null || name == null) {
            false
        }else{
            return repository.updateUser(updateUser!!, name!!)
        }

    }
}