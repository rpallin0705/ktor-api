package com.domain.usecase.user

import com.domain.models.User
import com.domain.repository.UserInterface

class GetAllUserUsersCase (val repository : UserInterface){
    suspend operator fun invoke(): List<User> = repository.getAllUsers()
}