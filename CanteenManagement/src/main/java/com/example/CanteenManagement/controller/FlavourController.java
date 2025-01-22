package com.example.CanteenManagement.controller;

import com.example.CanteenManagement.entity.Flavour;
import com.example.CanteenManagement.service.FlavourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("flavour")
public class FlavourController {

    private final FlavourService flavourService;

    public FlavourController(FlavourService flavourService) {
        this.flavourService = flavourService;
    }

    @GetMapping
    public List<Flavour> getAllFlavours() {
        return flavourService.getAll();
    }

    @PostMapping
    public Flavour createNewFlavour(@RequestBody Flavour flavour) {
        return flavourService.create(flavour);
    }

    @PutMapping("/{id}")
    public Flavour updateFlavour(@RequestBody Flavour flavour, @PathVariable Long id) {
        return flavourService.update(flavour, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        flavourService.delete(id);
    }

}
