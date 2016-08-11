package org.worldbridge.development.screenserver.rest.support;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LastSeenDateSerializerTest {
    @Test
    public void testSerialize() throws Exception {
        LastSeenDateSerializer serializer = new LastSeenDateSerializer();
        long timeStamp = 1470953236117l;

        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        serializer.serialize(new Date(timeStamp), jsonGenerator, null);

        verify(jsonGenerator, times(1)).writeString(eq("12 aug 00:07:16,117"));

    }

}