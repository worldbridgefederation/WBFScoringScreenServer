package org.worldbridge.development.screenserver.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.worldbridge.development.screenserver.dao.NotificationDao;
import org.worldbridge.development.screenserver.dao.ScreensDao;
import org.worldbridge.development.screenserver.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StatusServiceTest {
    private StatusService statusService;
    private NotificationDao notificationDao;
    private ScreensDao screensDao;

    @Before
    public void setUp() {
        notificationDao = mock(NotificationDao.class);
        screensDao = mock(ScreensDao.class);

        statusService = new StatusService();
        statusService.setNotificationDao(notificationDao);
        statusService.setScreensDao(screensDao);
    }

    @Test
    public void testStatusPut() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRemoteAddr()).thenReturn("1.1.1.1");

        when(notificationDao.getNotificationForDevice(any())).thenReturn(null);

        Status status = new Status();
        status.setDeviceId("deviceid");

        StatusResponse statusResponse = statusService.status(req, status);

        assertEquals(Boolean.FALSE, statusResponse.getShowNotitification());
        assertNull(statusResponse.getNotification());

        ArgumentCaptor<DeviceDetails> captor = ArgumentCaptor.forClass(DeviceDetails.class);
        verify(screensDao, times(1)).storeScreen(captor.capture());

        DeviceDetails deviceDetails = captor.getValue();
        assertEquals("deviceid", deviceDetails.getDeviceId());
        assertEquals("1.1.1.1", deviceDetails.getIpAddress());
        assertNotNull(deviceDetails.getLastSeen());
        assertNull(deviceDetails.getDeviceStatus());
    }

    @Test
    public void testStatusPutWithNotification() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRemoteAddr()).thenReturn("1.1.1.1");

        Notification notification = new Notification();
        notification.setTitle("testtitle");
        notification.setMessage("testmessage");
        when(notificationDao.getNotificationForDevice(any())).thenReturn(notification);

        Status status = new Status();
        status.setDeviceId("deviceid");

        StatusResponse statusResponse = statusService.status(req, status);

        assertEquals(Boolean.TRUE, statusResponse.getShowNotitification());
        assertNotNull(statusResponse.getNotification());
        assertEquals("testtitle", notification.getTitle());
        assertEquals("testmessage", notification.getMessage());

        ArgumentCaptor<DeviceDetails> captor = ArgumentCaptor.forClass(DeviceDetails.class);
        verify(screensDao, times(1)).storeScreen(captor.capture());

        DeviceDetails deviceDetails = captor.getValue();
        assertEquals("deviceid", deviceDetails.getDeviceId());
        assertEquals("1.1.1.1", deviceDetails.getIpAddress());
        assertNotNull(deviceDetails.getLastSeen());
        assertNull(deviceDetails.getDeviceStatus());
    }

    @Test
    public void testStatusPutWithFullDetails() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRemoteAddr()).thenReturn("1.1.1.1");

        when(notificationDao.getNotificationForDevice(any())).thenReturn(null);

        Status status = new Status();
        status.setDeviceId("deviceid");
        status.setCurrentUrl("http://www.example.com");
        status.setHardwareDetails(getHardwareDetails());
        status.setVersionDetails(getVersionDetails());
        status.setScreenDetails(getScreenDetails());

        StatusResponse statusResponse = statusService.status(req, status);

        assertEquals(Boolean.FALSE, statusResponse.getShowNotitification());
        assertNull(statusResponse.getNotification());

        ArgumentCaptor<DeviceDetails> captor = ArgumentCaptor.forClass(DeviceDetails.class);
        verify(screensDao, times(1)).storeScreen(captor.capture());

        DeviceDetails deviceDetails = captor.getValue();
        assertEquals("deviceid", deviceDetails.getDeviceId());
        assertEquals("1.1.1.1", deviceDetails.getIpAddress());
        assertNotNull(deviceDetails.getLastSeen());
        assertEquals("1.1 build 42",deviceDetails.getAppVersion());
        assertEquals("testmanufacturer", deviceDetails.getManufacturer());
        assertEquals("testmodel", deviceDetails.getModel());
        assertEquals("testserial", deviceDetails.getSerial());
        assertEquals("testrelease", deviceDetails.getVersion());
        assertEquals(1024, deviceDetails.getScreenDetails().getHeigth());
        assertEquals(768, deviceDetails.getScreenDetails().getWidth());
        assertNull(deviceDetails.getDeviceStatus());
    }

    private ScreenDetails getScreenDetails() {
        ScreenDetails screenDetails = new ScreenDetails();
        screenDetails.setHeigth(1024);
        screenDetails.setWidth(768);
        screenDetails.setXdpi(1.1f);
        screenDetails.setYdpi(2.2f);
        return screenDetails;
    }

    private VersionDetails getVersionDetails() {
        VersionDetails versionDetails = new VersionDetails();
        versionDetails.setVersionId("42");
        versionDetails.setVersionName("1.1");
        versionDetails.setApplicationId("com.test.app");
        return versionDetails;
    }

    private HardwareDetails getHardwareDetails() {
        HardwareDetails hardwareDetails = new HardwareDetails();
        hardwareDetails.setBrand("testbrand");
        hardwareDetails.setDevice("testdevice");
        hardwareDetails.setDisplay("testdisplay");
        hardwareDetails.setHardware("testhardware");
        hardwareDetails.setManufacturer("testmanufacturer");
        hardwareDetails.setModel("testmodel");
        hardwareDetails.setReleaseVersion("testrelease");
        hardwareDetails.setSerial("testserial");
        hardwareDetails.setType("testtype");
        return hardwareDetails;
    }


    @Test
    public void testStatusPutNullStatus() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRemoteAddr()).thenReturn("1.1.1.1");

        when(notificationDao.getNotificationForDevice(any())).thenReturn(null);

        try {
            statusService.status(req, null);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }

        verify(screensDao, times(0)).storeScreen(any());
        verify(notificationDao, times(0)).getNotificationForDevice(any());
    }

    @Test
    public void testGetScreenByAndroidId() {
        DeviceDetails details = new DeviceDetails();
        details.setDeviceId("testid");
        when(screensDao.getScreen(any())).thenReturn(details);

        DeviceDetails response = statusService.getScreenByAndroidId("testid");

        verify(screensDao, times(1)).getScreen(eq("testid"));

        assertEquals("testid", response.getDeviceId());
    }

    @Test
    public void testGetScreenByAndroidIdNotFound() {
        when(screensDao.getScreen(any())).thenReturn(null);

        try {
            statusService.getScreenByAndroidId("testid");
            fail("Expected a NotFoundException");
        } catch (NotFoundException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    @Test
    public void testOverview() {
        DeviceDetails details = new DeviceDetails();
        details.setDeviceId("testid");

        when(screensDao.listScreens()).thenReturn(Collections.singletonList(details));

        List<DeviceDetails> response = statusService.overview();

        assertEquals(1, response.size());

        verify(screensDao, times(1)).listScreens();
    }

}