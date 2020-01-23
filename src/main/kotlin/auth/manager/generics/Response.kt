package auth.manager.generics

class Response<CONTENT>(val content: CONTENT?,
                        val code: String?,
                        val message: String?) {

    class Builder<CONTENT> {
        private var content: CONTENT? = null
        private var code: String? = null
        private var message: String? = null

        fun content(content: CONTENT) = apply { this.content = content }
        fun code(code: String) = apply { this.code = code }
        fun message(message: String) = apply { this.message = message }

        fun build() = Response(content, code, message)
    }
}