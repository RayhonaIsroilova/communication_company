package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.SimCardDto;
import ecma.ai.ussdapp.service.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/simCard")
public class SimCardController {

    @Autowired
    SimCardService simCardService;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(simCardService.getSimCardList());
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id){
        ApiResponse simCardById = simCardService.getSimCardById(id);
        return ResponseEntity.status(simCardById.isSuccess()?200:409).body(simCardById);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody SimCardDto simCardDto){
        ApiResponse apiResponse = simCardService.AddSimCard(simCardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody SimCardDto simCardDto){
        ApiResponse apiResponse = simCardService.editSimCard(simCardDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse delete = simCardService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
