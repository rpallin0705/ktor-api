package com.domain.usecase.user

import com.domain.repository.UserInterface

class DeleteUserUseCase (val repository : UserInterface){
    var dni : String? = null

    suspend operator fun invoke() : Boolean {
        return if (dni == null) {
            false
        }else{
            return repository.deleteUser(dni!!)
        }
    }
}