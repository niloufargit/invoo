package com.invoo.orchestrator.client.invoice.service;

import com.invoo.orchestrator.domaine.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsService {

    private final IUserRepository userRepository;

    public UtilsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID getUserId(String userName) {
        return userRepository.getUserId(userName);
    }
}
