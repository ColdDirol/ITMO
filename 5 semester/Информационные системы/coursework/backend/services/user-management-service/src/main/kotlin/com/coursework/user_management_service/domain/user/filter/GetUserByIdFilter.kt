import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import com.newdex.services.contract.common.external.UserInfoDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetUserByIdFilter(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: Long): UserInfoDto {
        val user = userRepository.findById(id)
            .orElseThrow{ EntityNotFoundException("User with id $id not found") }

        return UserInfoDto(
            id = user.id,
            email = user.email,
            role = user.role,
            phone = user.phone,
            name = user.name,
            sex = user.sex.toString(),
            dateOfBirth = user.dateOfBirth,
            status = user.status,
        )
    }
}