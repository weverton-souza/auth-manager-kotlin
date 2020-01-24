package auth.manager.dto

data class AuthDTO(var id: String?,
                   var nickname: String?,
                   var username: String?,
                   var token: String?) {


    data class Builder(var id: String,
                       var nickname: String,
                       var username: String,
                       var token: String) {

        fun id(id: String) = apply { this.id = id }
        fun nickname(nickname: String) = apply { this.nickname = nickname }
        fun username(username: String) = apply { this.username = username }
        fun token(token: String) = apply { this.token = token }
        fun build() = AuthDTO(id, nickname, username, token)
    }

}