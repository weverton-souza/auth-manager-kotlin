package auth.manager.dto.interfaces

import auth.manager.domain.interfaces.IDomain
import java.io.Serializable

interface IDTO: Serializable {
    fun toDomain(): IDomain
}