package com.domain.usecase.user

import com.domain.repository.UserInterface

class DeleteUserUseCase (val repository : UserInterface){
    var email : String? = null

    suspend operator fun invoke() : Boolean {
        return if (email == null) {
            false
        }else{
            return repository.deleteUser(email!!)
        }
    }
}