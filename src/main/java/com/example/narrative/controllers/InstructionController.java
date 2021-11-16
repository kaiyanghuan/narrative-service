package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.InstructionRequest;
import com.example.narrative.controllers.responses.BriefInstructionResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.services.InstructionService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/instructions")
public class InstructionController {

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private RequestHelper requestHelper;

    @GetMapping
    public ResponseEntity<List<BriefInstructionResponse>> getAllInstructions() {
        return ok(instructionService.getInstructions().stream().map(instruction ->
                responseHelper.from(instruction).toBriefInstructionResponse()).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructionResponse> getInstruction(@PathVariable UUID id) {
        return ok(responseHelper.from(instructionService.getInstruction(id)).toInstructionResponse());
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<InstructionResponse> getInstruction(@PathVariable String name) {
        return ok(responseHelper.from(instructionService.getInstruction(name)).toInstructionResponse());
    }

    @PostMapping()
    public ResponseEntity<BriefInstructionResponse> createInstruction(
            @RequestBody InstructionRequest instructionRequest) {
        return ok(responseHelper.from(instructionService.create(
                requestHelper.from(instructionRequest).toInstruction())).toBriefInstructionResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BriefInstructionResponse> updateInstruction(
            @RequestBody InstructionRequest instructionRequest, @PathVariable UUID id) {
        return ok(responseHelper.from(instructionService.update(
                requestHelper.from(instructionRequest).toInstruction(), id)).toBriefInstructionResponse());
    }

    @PutMapping("/{name}/name")
    public ResponseEntity<BriefInstructionResponse> updateInstruction(
            @RequestBody InstructionRequest instructionRequest, @PathVariable String name) {
        return ok(responseHelper.from(instructionService.update(
                requestHelper.from(instructionRequest).toInstruction(), name)).toBriefInstructionResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InstructionResponse> deleteInstruction(@PathVariable UUID id) {
        return ok(responseHelper.from(instructionService.delete(id)).toInstructionResponse());
    }
}
