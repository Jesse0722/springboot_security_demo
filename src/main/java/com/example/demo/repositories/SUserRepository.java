package com.example.demo.repositories;



import com.example.demo.domain.SUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by jesse on 2017/2/12.
 */
public interface SUserRepository extends JpaRepository<SUser, Integer> {
    @Query("select u from SUser u where u.email=?1 and u.password=?2")
    SUser login(String email, String password);

    SUser findByName(String name);

    SUser findByEmailAndPassword(String email, String password);

    SUser findUserByEmail(String email);
}

