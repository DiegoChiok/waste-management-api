package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.RecyclingTipDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.services.RecyclingTipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wastemanagementapi")
public class RecyclingTipController {

    private final RecyclingTipService tipService;

    public RecyclingTipController(RecyclingTipService tipService) {
        this.tipService = tipService;
    }

    @PostMapping("/categories/{categoryId}/tips")
    public ResponseEntity<RecyclingTipDTO> createTip(
            @PathVariable Long categoryId,
            @RequestBody RecyclingTipDTO tipDTO) {
        try {
            RecyclingTip tip = new RecyclingTip(
                    null,
                    tipDTO.getTitle(),
                    tipDTO.getContent(),
                    null
            );

            RecyclingTip created = tipService.createTip(categoryId, tip);
            return new ResponseEntity<>(
                    RecyclingTipDTO.fromEntity(created),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tips/{id}")
    public ResponseEntity<RecyclingTipDTO> getTipById(@PathVariable Long id) {
        return tipService.getTipById(id)
                .map(tip -> new ResponseEntity<>(
                        RecyclingTipDTO.fromEntity(tip),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @GetMapping("/tips")
    public ResponseEntity<List<RecyclingTipDTO>> getAllTips() {
        List<RecyclingTipDTO> tipDTOs = tipService.getAllTips()
                .stream()
                .map(RecyclingTipDTO::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tipDTOs, HttpStatus.OK);
    }

    @PutMapping("/tips/{id}")
    public ResponseEntity<RecyclingTipDTO> updateTip(
            @PathVariable Long id,
            @RequestBody RecyclingTipDTO tipDTO) {
        try {
            RecyclingTip tip = new RecyclingTip(
                    id,
                    tipDTO.getTitle(),
                    tipDTO.getContent(),
                    null
            );

            RecyclingTip updated = tipService.updateTip(id, tip);
            return new ResponseEntity<>(
                    RecyclingTipDTO.fromEntity(updated),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/tips/{id}")
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        try {
            tipService.deleteTip(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}