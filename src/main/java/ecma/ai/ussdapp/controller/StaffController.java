package ecma.ai.ussdapp.controller;

import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.StaffDto;
import ecma.ai.ussdapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    //staff qo'shish
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> addStaff(@RequestBody StaffDto staffDto) {
        ApiResponse response = staffService.addStaff(staffDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_STAFF')")
    @GetMapping("/one/{username}")
    public HttpEntity<?> getStaff(@PathVariable String username){
        ApiResponse apiResponse = staffService.getStaff(username);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getStaffList(){
        List<Staff> staffList = staffService.getStaffList();
        return ResponseEntity.ok(staffList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_STAFF')")
    @PutMapping("/{username}")
    public HttpEntity<?> editStaff(@PathVariable String username, @RequestBody StaffDto staffDto){
        ApiResponse apiResponse = staffService.editStaff(username, staffDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/{username}")
    public HttpEntity<?> deleteStaff(@PathVariable String username){
        ApiResponse apiResponse = staffService.deleteStaff(username);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




}
