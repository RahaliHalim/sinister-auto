package com.gaconnecte.auxilium.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.expression.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class CustomJsonDateDeserializer extends JsonDeserializer<ZonedDateTime>
{
    @Override
    public ZonedDateTime deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

    	String date = jsonparser.getText();
    	Instant instant = Instant.parse(date);
    	ZonedDateTime zonedDateTime2 = instant.atZone(ZoneId.of("UTC"));
        
        try {
            return zonedDateTime2;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}