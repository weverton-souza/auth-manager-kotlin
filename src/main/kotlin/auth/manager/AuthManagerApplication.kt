package auth.manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing


@EnableMongoAuditing
@SpringBootApplication
class AuthManagerApplication
fun main(args: Array<String>) {
    runApplication<AuthManagerApplication>(*args)
}
