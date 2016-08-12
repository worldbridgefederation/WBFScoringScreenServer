package org.worldbridge.development.screenserver.rest.support;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationDateDeserializerTest {
    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"));
        Locale.setDefault(Locale.forLanguageTag("nl_NL"));
    }

    @Test
    public void testDeserialize() throws Exception {
        NotificationDateDeserializer serializer = new NotificationDateDeserializer();

        JsonParser jsonParser = mock(JsonParser.class);
        when(jsonParser.getText()).thenReturn("12-08-2016 00:07");
        Date date = serializer.deserialize(jsonParser, null);

        assertEquals(1470953220000L, date.getTime());
    }

}