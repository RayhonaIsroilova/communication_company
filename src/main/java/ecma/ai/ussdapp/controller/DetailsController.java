package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.entity.Details;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.DetailsDTO;
import ecma.ai.ussdapp.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/details")
public class DetailsController {

    @Autowired
    DetailsService detailsService;

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        List<Details> detailsAll = detailsService.getDetailsAll();
        return ResponseEntity.ok(detailsAll);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id){
        ApiResponse detailsById = detailsService.getDetailsById(id);
        return ResponseEntity.status(detailsById.isSuccess()?200:409).body(detailsById);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody DetailsDTO detailsDTO){
        ApiResponse apiResponse = detailsService.addDetails(detailsDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody DetailsDTO detailsDTO){
        ApiResponse apiResponse = detailsService.editDetails(detailsDTO, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value="hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse delete = detailsService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
