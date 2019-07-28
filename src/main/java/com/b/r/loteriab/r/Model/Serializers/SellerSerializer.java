package com.b.r.loteriab.r.Model.Serializers;

import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SellerSerializer extends StdSerializer<Seller> {

    public SellerSerializer() {
        this(null);
    }

    protected SellerSerializer(Class<Seller> t) {
        super(t);
    }

    @Override
    public void serialize(Seller value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("amountCharged", value.getAmountCharged());
        jgen.writeObjectField("paymentType", value.getPaymentType());
        jgen.writeEndObject();
    }
}
