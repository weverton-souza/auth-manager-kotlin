package auth.manager.enums

enum class Role {
    ROLE_ADMIN_LEV0, ROLE_CLIENT_LEV0;

    fun getAuthority(): String = name;
}
