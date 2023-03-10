package com.example.power;

import com.example.exception.EntityNotFound;
import jakarta.inject.Singleton;

@Singleton
public class PowerService {
    private final PowerRepository powerRepository;

    public PowerService(PowerRepository powerRepository) {
        this.powerRepository = powerRepository;
    }

    public Power get(Integer id){
        return powerRepository.findById(id).orElseThrow(()->new EntityNotFound("Power not exist with this id "+id));
    }
}
