package com.example.CanteenManagement.service;

import com.example.CanteenManagement.entity.Flavour;
import com.example.CanteenManagement.repository.FlavourRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class FlavourService {

    public final FlavourRepo flavourRepo;

    public FlavourService(FlavourRepo flavourRepo) {
        this.flavourRepo = flavourRepo;
    }


    public Flavour getOne(Long id) {
        return flavourRepo.findById(id).get();
    }

    public List<Flavour> getAll() {
        return flavourRepo.findAll();
    }

    public Flavour create(Flavour flavour) {
        return flavourRepo.save(flavour);
    }

    public Flavour update(Flavour flavour, Long id) {
        Optional<Flavour> flavourFromDb = flavourRepo.findById(id);

        if ( flavourFromDb.isEmpty()) {
            log.warn("flavour not found saving new flavour");
            return this.create(flavour);
        }

        flavourFromDb.get().setDescription(flavour.getDescription());
        return create(flavourFromDb.get());
    }

    public void delete(Long id) {

        flavourRepo.deleteById(id);

    }
}
