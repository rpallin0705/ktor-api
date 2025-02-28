package com.domain.usecase.user

import com.domain.repository.UserInterface

class DeleteUserUseCase (val repository : UserInterface){
    var name : String? = null

    suspend operator fun invoke() : Boolean {
        return if (name == null) {
            false
        }else{
            return repository.deleteUser(name!!)
        }
    }
}