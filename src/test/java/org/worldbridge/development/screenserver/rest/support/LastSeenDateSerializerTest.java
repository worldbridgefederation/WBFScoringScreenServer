package org.worldbridge.development.screenserver.rest.support;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class LastSeenDateSerializerTest {
    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"));
        Locale.setDefault(Locale.forLanguageTag("nl_NL"));
    }

    @Test
    public void testSerialize() throws Exception {
        LastSeenDateSerializer serializer = new LastSeenDateSerializer();
        long timeStamp = 1470953236117l;

        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        serializer.serialize(new Date(timeStamp), jsonGenerator, null);

        verify(jsonGenerator, times(1)).writeString(eq("12 Aug 00:07:16,117"));

    }

}