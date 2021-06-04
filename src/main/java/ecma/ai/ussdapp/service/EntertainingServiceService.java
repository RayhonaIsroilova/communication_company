package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.EntertainingService;
import ecma.ai.ussdapp.entity.Role;
import ecma.ai.ussdapp.entity.ServiceCategory;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.entity.enums.RoleName;
import ecma.ai.ussdapp.entity.enums.ServiceType;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.EntertainingServiceDTO;
import ecma.ai.ussdapp.repository.EntertainingServiceRepository;
import ecma.ai.ussdapp.repository.ServiceCategoryRepository;
import ecma.ai.ussdapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntertainingServiceService {
    @Autowired
    EntertainingServiceRepository entertainingServiceRepository;

    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    StaffRepository staffRepository;

    public List<EntertainingService> getAll() {
        return entertainingServiceRepository.findAll();
    }

    public ApiResponse getById(UUID id) {
        Optional<EntertainingService> byId = entertainingServiceRepository.findById(id);
        return byId.map(entertainingService -> new ApiResponse("mana ol", true, entertainingService)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addEnterService(EntertainingServiceDTO entertainingServiceDTO) {
        Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Staff> optionalStaff = staffRepository.findById(staff.getId());
        if (!optionalStaff.isPresent()) return new ApiResponse("User is not found", false);
        Staff currentStaff = optionalStaff.get();

        if (currentStaff.getRoles().contains(new Role(2, RoleName.ROLE_MANAGER))
                || currentStaff.getRoles().contains(new Role(1, RoleName.ROLE_DIRECTOR))) {
            if (entertainingServiceRepository.existsByName(entertainingServiceDTO.getName()))
                return new ApiResponse("E.S. is already exist", false);

            EntertainingService entertainingService = new EntertainingService();

            entertainingService.setName(entertainingServiceDTO.getName());
            entertainingService.setPrice(entertainingServiceDTO.getPrice());

            switch (entertainingServiceDTO.getServiceTypeNumber()) {
                case 1:
                    entertainingService.setServiceType(ServiceType.OVOZLI);
                    break;
                case 2:
                    entertainingService.setServiceType(ServiceType.MB);
                    break;
                case 3:
                    entertainingService.setServiceType(ServiceType.SMS);
                    break;
                default:
                    return new ApiResponse("Bunaqa TYPE yo'q,", false);
            }

            Optional<ServiceCategory> optionalServiceCategory = serviceCategoryRepository.findById(entertainingServiceDTO.getServiceCategoryId());
            if (!optionalServiceCategory.isPresent()) return new ApiResponse("Service category Id is not found", false);

            entertainingService.setServiceCategory(optionalServiceCategory.get());
            entertainingService.setExpireDate(new Timestamp(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 7));

            entertainingServiceRepository.save(entertainingService);
            return new ApiResponse("Entertaining Service is added successfully", true);
        }
        return new ApiResponse("You haven't got permission to add USSD CODE", false);
    }


    public ApiResponse editEnterService(UUID id, EntertainingServiceDTO entertainingServiceDTO) {
        Optional<EntertainingService> byId = entertainingServiceRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);

        Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Staff> optionalStaff = staffRepository.findById(staff.getId());
        if (!optionalStaff.isPresent()) return new ApiResponse("User is not found", false);
        Staff currentStaff = optionalStaff.get();

        if (currentStaff.getRoles().contains(new Role(2, RoleName.ROLE_MANAGER))
                || currentStaff.getRoles().contains(new Role(1, RoleName.ROLE_DIRECTOR))) {
            if (entertainingServiceRepository.existsByName(entertainingServiceDTO.getName()))
                return new ApiResponse("E.S. is already exist", false);

            EntertainingService entertainingService = byId.get();

            entertainingService.setName(entertainingServiceDTO.getName());
            entertainingService.setPrice(entertainingServiceDTO.getPrice());

            switch (entertainingServiceDTO.getServiceTypeNumber()) {
                case 1:
                    entertainingService.setServiceType(ServiceType.OVOZLI);
                    break;
                case 2:
                    entertainingService.setServiceType(ServiceType.MB);
                    break;
                case 3:
                    entertainingService.setServiceType(ServiceType.SMS);
                    break;
                default:
                    return new ApiResponse("Bunaqa TYPE yo'q,", false);
            }

            Optional<ServiceCategory> optionalServiceCategory = serviceCategoryRepository.findById(entertainingServiceDTO.getServiceCategoryId());
            if (!optionalServiceCategory.isPresent()) return new ApiResponse("Service category Id is not found", false);

            entertainingService.setServiceCategory(optionalServiceCategory.get());
            entertainingService.setExpireDate(new Timestamp(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 7));

            entertainingServiceRepository.save(entertainingService);
            return new ApiResponse("Entertaining Service is edited successfully", true);
        }
        return new ApiResponse("You haven't got permission to add USSD CODE", false);
    }


    public ApiResponse delete(UUID id) {
        Optional<EntertainingService> byId = entertainingServiceRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        entertainingServiceRepository.deleteById(id);
        return new ApiResponse("Delete successfully", true);
    }
}
