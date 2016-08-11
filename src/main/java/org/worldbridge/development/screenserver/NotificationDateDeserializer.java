package org.worldbridge.development.screenserver;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = jsonParser.getText();
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Failed to deserialize date " + dateString, e);
        }
    }
}
