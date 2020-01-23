package auth.manager.exceptions

import org.springframework.http.HttpStatus


class ExceptionHandler(override val message: String,
                       val httpStatus: HttpStatus): RuntimeException()