package com.airport.vms.service;

import com.airport.vms.domain.IdempotencyKey;
import com.airport.vms.repository.IdempotencyKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class IdempotencyService {

    private final IdempotencyKeyRepository idempotencyKeyRepository;

    public IdempotencyService(IdempotencyKeyRepository idempotencyKeyRepository) {
        this.idempotencyKeyRepository = idempotencyKeyRepository;
    }

    public boolean isDuplicate(String key) {
        if (idempotencyKeyRepository.findByIdempotencyKey(key).isPresent()) {
            return true;
        }
        idempotencyKeyRepository.save(new IdempotencyKey(key));
        return false;
    }
}
