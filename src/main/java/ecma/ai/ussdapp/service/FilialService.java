package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Filial;
import ecma.ai.ussdapp.entity.Role;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.entity.enums.RoleName;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.FilialDto;
import ecma.ai.ussdapp.repository.FilialRepository;
import ecma.ai.ussdapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilialService {

    @Autowired
    FilialRepository filialRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StaffRepository staffRepository;

    public ApiResponse addFilial(FilialDto filialDto) {

        Staff user = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Filial filial = new Filial();
        filial.setName(filialDto.getName());

        //shu filialni directori
        Staff staff = new Staff();
        staff.setUserName(filialDto.getStaffDto().getUserName());
        staff.setFullName(filialDto.getStaffDto().getFullName());
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role(1, RoleName.ROLE_DIRECTOR);
        roleSet.add(role);
        staff.setRoles(roleSet);

        staff.setPosition(filialDto.getStaffDto().getPosition());
        staff.setFilial(filial);
        staff.setPassword(passwordEncoder.encode(filialDto.getStaffDto().getPassword()));
        staffRepository.save(staff);
        filial.setDirector(staff);

        List<Staff> staffList = new ArrayList<>();

        if (filialDto.getStaffUsernames() != null) {
            for (String staffUsername : filialDto.getStaffUsernames()) {

                Optional<Staff> optionalStaff = staffRepository.findByUserName(staffUsername);
                if (!optionalStaff.isPresent()) {
                    return new ApiResponse("Staff yoqku?", false);
                }
                staffList.add(optionalStaff.get());
            }
        }
        filial.setStaffList(staffList);
        filialRepository.save(filial);
        return new ApiResponse("Filial added!", true);
    }

    public ApiResponse editFilial(UUID id, FilialDto filialDto) {
        Optional<Filial> byId = filialRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        Filial filial = byId.get();
        filial.setName(filialDto.getName());

        //shu filialni directori
        Staff staff = new Staff();
        staff.setUserName(filialDto.getStaffDto().getUserName());
        staff.setFullName(filialDto.getStaffDto().getFullName());
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role(1, RoleName.ROLE_DIRECTOR);
        roleSet.add(role);
        staff.setRoles(roleSet);

        staff.setPosition(filialDto.getStaffDto().getPosition());
        staff.setFilial(filial);
        staff.setPassword(passwordEncoder.encode(filialDto.getStaffDto().getPassword()));
        staffRepository.save(staff);
        filial.setDirector(staff);

        List<Staff> staffList = new ArrayList<>();

        if (filialDto.getStaffUsernames() != null) {
            for (String staffUsername : filialDto.getStaffUsernames()) {

                Optional<Staff> optionalStaff = staffRepository.findByUserName(staffUsername);
                if (!optionalStaff.isPresent()) {
                    return new ApiResponse("Staff yoqku?", false);
                }
                staffList.add(optionalStaff.get());
            }
        }
        filial.setStaffList(staffList);
        filialRepository.save(filial);
        return new ApiResponse("Filial edited!", true);

    }

    public ApiResponse getFilialById(UUID id){
        Optional<Filial> byId = filialRepository.findById(id);
        return byId.map(filial -> new ApiResponse("=>", true, filial)).orElseGet(() -> new ApiResponse("Filial not found", false));
    }

    public List<Filial> getFilialByList(){
        return filialRepository.findAll();
    }

    public ApiResponse deleteFilial(UUID id){
        Optional<Filial> byId = filialRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This id not found",false);
        filialRepository.deleteById(id);
        return new ApiResponse("Delete success!",true);
    }

}
