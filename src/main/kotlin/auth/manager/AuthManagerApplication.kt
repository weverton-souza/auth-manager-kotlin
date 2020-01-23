package auth.manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@SpringBootApplication
@EnableMongoAuditing
class AuthManagerApplication

fun main(args: Array<String>) {
    runApplication<AuthManagerApplication>(*args)
}
