package auth.manager.domain.interfaces

import auth.manager.dto.interfaces.IDTO
import java.io.Serializable

interface IDomain: Serializable {
    fun toDTO(): IDTO
}
