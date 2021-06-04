package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.EntertainingServiceDTO;
import ecma.ai.ussdapp.service.EntertainingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/entertainingService")
public class EntertainingServiceController {

    @Autowired
    EntertainingServiceService entertainingServiceService;

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(entertainingServiceService.getAll());
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id){
        ApiResponse byId = entertainingServiceService.getById(id);
        return ResponseEntity.status(byId.isSuccess()?200:409).body(byId);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody EntertainingServiceDTO entertainingServiceDTO){
        ApiResponse apiResponse = entertainingServiceService.addEnterService(entertainingServiceDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody EntertainingServiceDTO entertainingServiceDTO){
        ApiResponse apiResponse = entertainingServiceService.editEnterService(id, entertainingServiceDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse delete = entertainingServiceService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
