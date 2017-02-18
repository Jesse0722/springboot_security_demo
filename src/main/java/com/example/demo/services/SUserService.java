package com.example.demo.services;


import com.example.demo.domain.SUser;
import com.example.demo.repositories.SUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jesse on 2017/2/12.
 */
@Service("suserService")
public class SUserService {

    @Autowired
    private SUserRepository suserRepository;//code10

    public List<SUser> findAll() {
        return suserRepository.findAll();
    }

    public SUser create(SUser user) {
        return suserRepository.save(user);
    }

    public SUser findUserById(int id) {
        return suserRepository.findOne(id);
    }

    public SUser login(String email, String password) {
        return suserRepository.findByEmailAndPassword(email, password);
    }

    public SUser update(SUser user) {
        return suserRepository.save(user);
    }

    public void deleteUser(int id) {
        suserRepository.delete(id);
    }

    public SUser findByName(String name) {
        return suserRepository.findByName(name);
    }

}

