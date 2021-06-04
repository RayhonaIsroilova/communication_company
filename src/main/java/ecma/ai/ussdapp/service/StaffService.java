package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Filial;
import ecma.ai.ussdapp.entity.Role;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.StaffDto;
import ecma.ai.ussdapp.repository.FilialRepository;
import ecma.ai.ussdapp.repository.RoleRepository;
import ecma.ai.ussdapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StaffService {
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    FilialRepository filialRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse addStaff(StaffDto staffDto) {
        Optional<Role> optionalRole = roleRepository.findById(staffDto.getRoleId());
        if (!optionalRole.isPresent()){
            return new ApiResponse("Such Role doesnt exist",false);
        }
        Optional<Filial> optionalFilial = filialRepository.findById(staffDto.getFilialId());
        if (!optionalFilial.isPresent()){
            return new ApiResponse("Such Filial doesnt exist",false);
        }
        Staff staff=new Staff();
        staff.setFullName(staffDto.getFullName());
        staff.setUserName(staffDto.getUserName());
        Set<Role> roles=new HashSet<>();
        roles.add(optionalRole.get());
        staff.setRoles(roles);
        staff.setFilial(optionalFilial.get());
        staff.setPosition(staffDto.getPosition());
        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        staffRepository.save(staff);
        return new ApiResponse("Staff added!",true);
    }

    public List<Staff> getStaffList(){
        return staffRepository.findAll();
    }

    public ApiResponse getStaff(String username){
        Optional<Staff> optionalStaff = staffRepository.findByUserName(username);
        return optionalStaff.map(staff -> new ApiResponse("Success", true, staff)).orElseGet(() -> new ApiResponse("Such staff doesn't exist", false));
    }

    public ApiResponse editStaff(String username,StaffDto staffDto){
        Optional<Staff> optionalStaff = staffRepository.findByUserName(username);
        if (!optionalStaff.isPresent()){
            return new ApiResponse("Such Staff doesnt exist",false);
        }
        Optional<Filial> optionalFilial = filialRepository.findById(staffDto.getFilialId());
        if (!optionalFilial.isPresent()){
            return new ApiResponse("Such Filial doesnt exist",false);
        }
        Staff staff = optionalStaff.get();
        staff.setFullName(staffDto.getFullName());
        boolean existsByUserName = staffRepository.existsByUserName(staff.getUsername());
        if (existsByUserName){
            return new ApiResponse("Such staff already exist",false);
        }
        staff.setUserName(staffDto.getUserName());
        staff.setFilial(optionalFilial.get());
        staff.setPosition(staffDto.getPosition());
        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        staffRepository.save(staff);
        return new ApiResponse("Staff edited!",true);
    }

    public ApiResponse deleteStaff(String username){
        Optional<Staff> optionalStaff = staffRepository.findByUserName(username);
        if (!optionalStaff.isPresent()){
            return new ApiResponse("Such staff doesnt exist",false);
        }
        staffRepository.delete(optionalStaff.get());
        return new ApiResponse("Staff deleted",true);
    }

}
