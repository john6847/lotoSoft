package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.Products;
import com.b.r.loteriab.r.Model.Users;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<Users> {

    public UserSerializer() {
        this(null);
    }

    protected UserSerializer(Class<Users> t) {
        super(t);
    }

    @Override
    public void serialize(Users value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeObjectField("token", value.getToken());
        jgen.writeObjectField("username", value.getUsername());
        jgen.writeEndObject();
    }
}
