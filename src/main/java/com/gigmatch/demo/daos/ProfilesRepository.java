package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilesRepository extends JpaRepository<Profile, Long> {

    Profile findProfileByUserId(long id);
    //should this change to user.id?  if User is the OneToOne relationship

}
