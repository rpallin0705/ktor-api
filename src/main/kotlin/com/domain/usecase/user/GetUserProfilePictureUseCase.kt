package com.domain.usecase.user

import com.domain.repository.UserInterface

class GetUserProfilePictureUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(email: String): String? {
        if (email.isBlank()) return null
        return repository.getUserProfilePicture(email)
    }
}