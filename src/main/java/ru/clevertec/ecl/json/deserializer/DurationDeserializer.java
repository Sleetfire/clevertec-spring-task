package ru.clevertec.ecl.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class DurationDeserializer extends StdDeserializer<Duration> {

    protected DurationDeserializer(Class<?> vc) {
        super(vc);
    }

    public DurationDeserializer() {
        this(null);
    }

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        return Duration.ofDays(p.getValueAsLong());
    }
}
