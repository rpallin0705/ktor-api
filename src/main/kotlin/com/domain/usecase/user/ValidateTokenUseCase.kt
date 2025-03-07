import com.domain.repository.UserInterface

class ValidateTokenUseCase(private val repository: UserInterface) {
    suspend operator fun invoke(email: String, token: String): Boolean {
        return repository.validateToken(email, token)
    }
}
