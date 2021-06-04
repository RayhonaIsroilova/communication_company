package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Role;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.entity.UssdCode;
import ecma.ai.ussdapp.entity.enums.RoleName;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.UssdCodeDTO;
import ecma.ai.ussdapp.repository.StaffRepository;
import ecma.ai.ussdapp.repository.USSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UssdCodeService {
    @Autowired
    USSDRepository ussdRepository;

    @Autowired
    StaffRepository staffRepository;

    public ApiResponse addUssd(UssdCodeDTO ussdCodeDTO){
        Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Staff> optionalStaff = staffRepository.findById(staff.getId());
        if (!optionalStaff.isPresent()) return new ApiResponse("User is not found", false);
        Staff currentStaff = optionalStaff.get();

        if (currentStaff.getRoles().contains(new Role(2,RoleName.ROLE_MANAGER))
                ||currentStaff.getRoles().contains(new Role(1,RoleName.ROLE_DIRECTOR))){
            UssdCode ussdCode= new UssdCode();
            ussdCode.setCode(ussdCodeDTO.getCode());
            ussdCode.setDescription(ussdCodeDTO.getDescription());
            ussdRepository.save(ussdCode);
            return new ApiResponse("UssdCode is added successfully", true);
        }
        return new ApiResponse("You haven't got permission to add USSD CODE", false);

    }

    public ApiResponse editUssd(UUID id, UssdCodeDTO ussdCodeDTO){
        Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Staff> optionalStaff = staffRepository.findById(staff.getId());
        if (!optionalStaff.isPresent()) return new ApiResponse("User is not found", false);
        Staff currentStaff = optionalStaff.get();
        Optional<UssdCode> byId = ussdRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This id not found",false);

        if (currentStaff.getRoles().contains(new Role(2,RoleName.ROLE_MANAGER))
                ||currentStaff.getRoles().contains(new Role(1,RoleName.ROLE_DIRECTOR))){
            UssdCode ussdCode = byId.get();
            ussdCode.setCode(ussdCodeDTO.getCode());
            ussdCode.setDescription(ussdCodeDTO.getDescription());
            ussdRepository.save(ussdCode);
            return new ApiResponse("UssdCode is edited successfully", true);
        }
        return new ApiResponse("You haven't got permission to add USSD CODE", false);

    }

    public ApiResponse getUssdById(UUID id){
        Optional<UssdCode> byId = ussdRepository.findById(id);
        if (!byId.isPresent()) return  new ApiResponse("This id not found",false);
        UssdCode ussdCode = byId.get();
        return new ApiResponse("mana ussd =>",true,ussdCode);
    }

    public List<UssdCode> getUssdByList(){
        return ussdRepository.findAll();
    }

    public ApiResponse deleteUssd(UUID id){
        Optional<UssdCode> byId = ussdRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This id not found",false);
        ussdRepository.deleteById(id);
        return new ApiResponse("delete success",true);
    }



}
