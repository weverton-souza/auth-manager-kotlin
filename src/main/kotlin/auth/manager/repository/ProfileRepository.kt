package auth.manager.repository

import auth.manager.domain.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfileRepository: MongoRepository<Profile, String> {
    fun findByUsername(userName: String): Optional<Profile>
}