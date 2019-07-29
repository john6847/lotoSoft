package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.Products;
import com.b.r.loteriab.r.Model.Shift;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ShiftSerializer extends StdSerializer<Shift> {

    public ShiftSerializer() {
        this(null);
    }

    protected ShiftSerializer(Class<Shift> t) {
        super(t);
    }

    @Override
    public void serialize(Shift value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeObjectField("openTime", value.getOpenTime());
        jgen.writeObjectField("closeTime", value.getCloseTime());
        jgen.writeObjectField("enabled", value.isEnabled());
        jgen.writeEndObject();
    }
}
