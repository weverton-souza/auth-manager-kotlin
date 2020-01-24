package auth.manager.service

import org.springframework.stereotype.Service

@Service
class TesterService {
    fun adminAccess(): String = "U has a admin profile"
    fun userAccess(): String = "U has a regular user profile"
    fun userAndAdminAccess(): String = "U has a regular user and admin profiles"
}