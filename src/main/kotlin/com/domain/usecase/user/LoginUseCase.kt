package com.domain.usecase.user

import com.domain.repository.UserInterface

class LoginUseCase (val repository : UserInterface){
    suspend operator fun invoke(name: String ?, pass:String ?): Boolean {
        return if (name.isNullOrBlank() || pass.isNullOrBlank())
           false
        else {
            try{
                val res = repository.login(name, pass)
                res
            }catch (e: Exception){
                println("Error en login:  ${e.localizedMessage}")
                false
            }

        }
    }
}