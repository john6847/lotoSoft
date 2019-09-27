package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.CombinationType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CombinationTypeSerializer extends StdSerializer<CombinationType> {

    public CombinationTypeSerializer() {
        this(null);
    }

    protected CombinationTypeSerializer(Class<CombinationType> t) {
        super(t);
    }

    @Override
    public void serialize(CombinationType value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("products", value.getProducts());
        jgen.writeNumberField("payedPrice", value.getPayedPrice());
        jgen.writeEndObject();
    }
}
