package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.Products;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ProductSerializer extends StdSerializer<Products> {

    public ProductSerializer() {
        this(null);
    }

    protected ProductSerializer(Class<Products> t) {
        super(t);
    }

    @Override
    public void serialize(Products value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeEndObject();
    }
}
