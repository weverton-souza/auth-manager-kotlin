package auth.manager.enumeration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.io.IOException

enum class Role(val text: String) {
    ROLE_ADMIN_LEV0("Administrator"),
    ROLE_USER_LEV0("User");

    internal class RoleDeserializer : JsonDeserializer<Role>() {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserialize(jsonParser: JsonParser, ctx: DeserializationContext?): Role {
            val oc: ObjectCodec = jsonParser.codec
            val node: JsonNode = oc.readTree(jsonParser)
            return valueOf(node["name"].asText())
        }
    }
}
