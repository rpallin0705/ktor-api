package com.domain.usecase.user

import com.domain.repository.UserInterface

class UploadUserProfilePictureUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(email: String, imagePath: String): Boolean {
        return repository.uploadUserProfilePicture(email, imagePath)
    }
}