package org.worldbridge.development.screenserver.persistance;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
    Collection<NotificationEntity> findByDevice(ScreenEntity device);

    Collection<NotificationEntity> findByGroup(ScreenGroupEntity group);
}
