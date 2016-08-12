package org.worldbridge.development.screenserver.rest;

import org.junit.Before;
import org.junit.Test;
import org.worldbridge.development.screenserver.dao.DaoException;
import org.worldbridge.development.screenserver.dao.ScreenGroupDao;
import org.worldbridge.development.screenserver.domain.DeviceDetails;
import org.worldbridge.development.screenserver.domain.ScreenGroup;

import javax.ws.rs.BadRequestException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScreenGroupServiceTest {
    private ScreenGroupService screenGroupService;
    private ScreenGroupDao screenGroupDao;

    @Before
    public void setUp() {
        screenGroupDao = mock(ScreenGroupDao.class);

        screenGroupService = new ScreenGroupService();
        screenGroupService.setScreenGroupDao(screenGroupDao);
    }

    @Test
    public void testList() {
        ScreenGroup screenGroup = getScreenGroup();

        when(screenGroupDao.listScreenGroups()).thenReturn(Collections.singletonList(screenGroup));

        List<ScreenGroup> result = screenGroupService.list();
        assertEquals(1, result.size());
    }

    @Test
    public void testAddScreenGroup() {
        ScreenGroup screenGroup = getScreenGroup();
        screenGroupService.addScreenGroup(screenGroup);

        verify(screenGroupDao, times(1)).createScreenGroup(eq(screenGroup.getGroupName()));
    }

    @Test
    public void testAddScreenGroupNull() {
        try {
            screenGroupService.addScreenGroup(null);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    @Test
    public void testAddScreenGroupWithDevices() {
        ScreenGroup screenGroup = getScreenGroup();
        screenGroup.setDevices(Collections.singletonList(getDeviceDetails()));

        try {
            screenGroupService.addScreenGroup(screenGroup);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }
    }

    @Test
    public void testAddScreenGroupWithNullDevices() {
        ScreenGroup screenGroup = getScreenGroup();
        screenGroup.setDevices(null);

        screenGroupService.addScreenGroup(screenGroup);

        verify(screenGroupDao, times(1)).createScreenGroup(eq(screenGroup.getGroupName()));
    }

    @Test
    public void testAddDeviceToGroup() throws Exception {
        screenGroupService.addDeviceToGroup("testgroup", getDeviceDetails());

        verify(screenGroupDao, times(1)).addScreenToScreenGroup(eq("testgroup"), eq("testid"));
    }

    @Test
    public void removeDeviceFromGroup() throws Exception {
        screenGroupService.removeDeviceFromGroup("testgroup", getDeviceDetails());

        verify(screenGroupDao, times(1)).removeScreenFromScreenGroup(eq("testgroup"), eq("testid"));
    }

    @Test
    public void testAddDeviceToGroupNullDevice() throws Exception {
        try {
            screenGroupService.addDeviceToGroup("testgroup", null);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }

        verify(screenGroupDao, times(0)).addScreenToScreenGroup(any(), any());
    }

    @Test
    public void testRemoveDeviceFromGroupNullDevice() throws Exception {
        try {
            screenGroupService.removeDeviceFromGroup("testgroup", null);
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }

        verify(screenGroupDao, times(0)).removeScreenFromScreenGroup(any(), any());
    }

    @Test
    public void testAddDeviceToGroupWithNonExistingGroup() throws Exception {
        doThrow(new DaoException()).when(screenGroupDao).addScreenToScreenGroup(anyString(), anyString());

        try {
            screenGroupService.addDeviceToGroup("notexisting", getDeviceDetails());
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }

        verify(screenGroupDao, times(1)).addScreenToScreenGroup(eq("notexisting"), any());
    }

    @Test
    public void testRemoveDeviceFromGroupWithNonExistingGroup() throws Exception {
        doThrow(new DaoException()).when(screenGroupDao).removeScreenFromScreenGroup(anyString(), anyString());
        try {
            screenGroupService.removeDeviceFromGroup("notexisting", getDeviceDetails());
            fail();
        } catch (BadRequestException e) {
            assertNull(e.getResponse().getEntity());
        }

        verify(screenGroupDao, times(1)).removeScreenFromScreenGroup(eq("notexisting"), any());
    }

    private ScreenGroup getScreenGroup() {
        ScreenGroup screenGroup = new ScreenGroup();
        screenGroup.setGroupName("testgroup");
        screenGroup.setDevices(Collections.emptyList());
        return screenGroup;
    }

    private DeviceDetails getDeviceDetails() {
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setDeviceId("testid");
        return deviceDetails;
    }

}