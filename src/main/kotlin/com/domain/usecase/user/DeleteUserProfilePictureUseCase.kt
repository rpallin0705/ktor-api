package com.domain.usecase.user

import com.domain.repository.UserInterface

class DeleteUserProfilePictureUseCase(private val repository: UserInterface) {

    suspend operator fun invoke(email: String?): Boolean {
        return repository.deleteUserProfilePicture(email!!)
    }
}