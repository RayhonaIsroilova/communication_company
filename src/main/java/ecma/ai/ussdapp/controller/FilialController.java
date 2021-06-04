package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.FilialDto;
import ecma.ai.ussdapp.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/filial")
public class FilialController {
    @Autowired
    FilialService filialService;

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(filialService.getFilialByList());
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id){
        ApiResponse filialById = filialService.getFilialById(id);
        return ResponseEntity.status(filialById.isSuccess()?200:409).body(filialById);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> addFilial(@RequestBody FilialDto filialDto) {
        ApiResponse response = filialService.addFilial(filialDto);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody FilialDto filialDto){
        ApiResponse apiResponse = filialService.editFilial(id, filialDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = filialService.deleteFilial(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
