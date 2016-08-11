package org.worldbridge.development.screenserver.rest.support;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class NotificationDateSerializerTest {
    @Test
    public void testSerialize() throws Exception {
        NotificationDateSerializer serializer = new NotificationDateSerializer();
        long timeStamp = 1470953236117l;

        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        serializer.serialize(new Date(timeStamp), jsonGenerator, null);

        verify(jsonGenerator, times(1)).writeString(eq("12-08-2016 00:07"));
    }

}