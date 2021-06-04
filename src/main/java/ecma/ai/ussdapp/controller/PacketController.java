package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.PacketDTO;
import ecma.ai.ussdapp.payload.PacketDTOClient;
import ecma.ai.ussdapp.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/packet")
public class PacketController {

    @Autowired
    PacketService packetService;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(packetService.getPacketAll());
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id){
        ApiResponse packetById = packetService.getPacketById(id);
        return ResponseEntity.status(packetById.isSuccess()?200:409).body(packetById);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody PacketDTO packetDTO){
        ApiResponse apiResponse = packetService.addPacket(packetDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody PacketDTOClient packetDTOClient){
        ApiResponse apiResponse = packetService.editPacket(packetDTOClient, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse delete = packetService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
