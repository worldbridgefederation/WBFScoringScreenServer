package org.worldbridge.development.screenserver.rest.support;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class NotificationDateSerializerTest {
    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"));
        Locale.setDefault(Locale.forLanguageTag("nl_NL"));
    }
    @Test
    public void testSerialize() throws Exception {
        NotificationDateSerializer serializer = new NotificationDateSerializer();
        long timeStamp = 1470953236117L;

        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        serializer.serialize(new Date(timeStamp), jsonGenerator, null);

        verify(jsonGenerator, times(1)).writeString(eq("12-08-2016 00:07"));
    }

}