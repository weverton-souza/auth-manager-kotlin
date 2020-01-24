package auth.manager.enumeration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.IOException

@JsonSerialize(using = SimpleEnumJsonSerializer::class)
@JsonDeserialize(using = Role.RoleDeserializer::class)
class SimpleEnumJsonSerializer: JsonSerializer<SimpleEnum>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(simpleEnum: SimpleEnum, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        jsonGenerator.writeStartObject()
        jsonGenerator.writeStringField("name", simpleEnum.toString())
        jsonGenerator.writeStringField("text", simpleEnum.getText())
        jsonGenerator.writeEndObject()
    }

    override fun handledType(): Class<SimpleEnum> {
        return SimpleEnum::class.java
    }
}
