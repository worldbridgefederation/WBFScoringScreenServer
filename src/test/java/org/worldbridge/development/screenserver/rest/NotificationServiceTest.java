package org.worldbridge.development.screenserver.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.worldbridge.development.screenserver.dao.DaoException;
import org.worldbridge.development.screenserver.dao.NotificationDao;
import org.worldbridge.development.screenserver.domain.NotificationDetails;

import javax.ws.rs.BadRequestException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {
    private NotificationService notificationService;
    private NotificationDao notificationDao;

    @Before
    public void setUp() {
        notificationDao = mock(NotificationDao.class);

        notificationService = new NotificationService();
        notificationService.setNotificationDao(notificationDao);
    }

    @Test
    public void testListNotifications() {
        NotificationDetails details = getNotificationDetails();

        when(notificationDao.loadNotifications()).thenReturn(Collections.singletonList(details));

        List<NotificationDetails> result = notificationService.getNotifications();
        assertEquals(1, result.size());
    }

    @Test
    public void testAddNotification() throws Exception {
        NotificationDetails details = getNotificationDetails();

        notificationService.addNotification(details);

        ArgumentCaptor<NotificationDetails> captor = ArgumentCaptor.forClass(NotificationDetails.class);

        verify(notificationDao, times(1)).addNotification(captor.capture());
        NotificationDetails captured = captor.getValue();
        assertEquals("testtitle", captured.getTitle());
        assertEquals("testmessage", captured.getMessage());
    }

    @Test
    public void testAddNotificationDaoException() throws Exception {
        doThrow(new DaoException("mocked exception")).when(notificationDao).addNotification(any());
        try {
            notificationService.addNotification(getNotificationDetails());
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    @Test
    public void testAddNotificationWithNull() {
        try {
            notificationService.addNotification(null);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    @Test
    public void testAddNotificationWithEmptyField() {
        NotificationDetails details = getNotificationDetails();
        details.setValidTo(null);
        details.setTitle(null);

        try {
            notificationService.addNotification(details);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    private NotificationDetails getNotificationDetails() {
        NotificationDetails details = new NotificationDetails();
        details.setTitle("testtitle");
        details.setMessage("testmessage");
        details.setValidTo(new Date(System.currentTimeMillis() + 300000));
        details.setValidFrom(new Date());
        details.setTarget("deviceid");

        return details;
    }

}