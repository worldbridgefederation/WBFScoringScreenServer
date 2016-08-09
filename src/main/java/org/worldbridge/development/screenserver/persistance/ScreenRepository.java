package org.worldbridge.development.screenserver.persistance;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ScreenRepository extends CrudRepository<ScreenEntity, String> {
    Collection<ScreenEntity> findByScreenGroup(ScreenGroupEntity screenGroupEntity);
}
