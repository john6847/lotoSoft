package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.Role;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class RolesSerializer extends StdSerializer<Role> {

    public RolesSerializer() {
        this(null);
    }

    protected RolesSerializer(Class<Role> t) {
        super(t);
    }

    @Override
    public void serialize(Role value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeEndObject();
    }
}
