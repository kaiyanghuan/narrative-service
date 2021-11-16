package com.example.narrative.services;

import com.example.narrative.authentications.UserContext;
import com.example.narrative.entities.Blueprint;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.BlueprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static com.example.narrative.repositories.BlueprintSpecifications.searchKeyword;

@Service
public class BlueprintService {

    @Autowired
    private BlueprintRepository blueprintRepository;

    @Autowired
    private UserService userService;

    public List<Blueprint> getBlueprints() {
        return blueprintRepository.findAll();
    }

    public Page<Blueprint> searchBlueprint(String keyword, Pageable pageable) {
        return blueprintRepository.findAll((Specification) searchKeyword(keyword), pageable);
    }

    public Blueprint getBlueprint(UUID id) {
        return blueprintRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Blueprint create(Blueprint blueprint) {
        blueprint.setUserId(userService.getUserId());
        blueprint.setUsername(UserContext.loggedInUsername());
        return blueprintRepository.save(blueprint);
    }

    public Blueprint adoptBlueprint(UUID id) {
        Blueprint blueprint = getBlueprint(id);
        blueprint.setAdoptionRate(blueprint.getAdoptionRate().add(BigInteger.ONE));
        return blueprintRepository.save(blueprint);
    }
}