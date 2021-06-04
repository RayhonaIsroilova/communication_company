package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.component.DetailsniExseldanOlishUchun;
import ecma.ai.ussdapp.entity.Details;
import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.DetailsDTO;
import ecma.ai.ussdapp.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DetailsService {

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    DetailsniExseldanOlishUchun detailsniExseldanOlishUchun;

    public List<Details> getDetailsAll(){
        return detailsRepository.findAll();
    }

    public ApiResponse getDetailsById(UUID uuid){
        Optional<Details> byId = detailsRepository.findById(uuid);
        return byId.map(details -> new ApiResponse("Mana ol", true, details)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addDetails(DetailsDTO detailsDTO) {
        SimCard principal = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getId().toString().equals(detailsDTO.getSimCard().getId())){
            Details details = new Details();
            details.setActionType(detailsDTO.getActionType());
            details.setSimCard(detailsDTO.getSimCard());
            details.setAmount(detailsDTO.getAmount());
            detailsRepository.save(details);
            return new ApiResponse("Saved success",true,details);
        }
        return new ApiResponse("Cho'ta saqlanmadi, qayerdadir hato qilding! qara yaxshiroq?",false);
    }

    public ApiResponse exportExcel(HttpServletResponse response, List<Details> details){
        response.setContentType("application/octet-stream");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String format = dateFormat.format(new Date());

        String header="Content-Disposition";
        String value="attachment; filename=user_"+format+".xlsx";

        response.setHeader(header,value);
        detailsniExseldanOlishUchun.takeExcel(response,details);
        return new ApiResponse("Urraa successfully exportter",true);
    }

    public ApiResponse editDetails(DetailsDTO detailsDTO,UUID id){
        Optional<Details> byId = detailsRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        SimCard principal = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getId().toString().equals(detailsDTO.getSimCard().getId()))
            return new ApiResponse("Hato brat adashding",false);
        Details details = byId.get();
        details.setAmount(detailsDTO.getAmount());
        details.setSimCard(detailsDTO.getSimCard());
        details.setActionType(detailsDTO.getActionType());
        detailsRepository.save(details);
        return new ApiResponse("Edited successfully",true);
    }

    public ApiResponse delete(UUID uuid){
        Optional<Details> byId = detailsRepository.findById(uuid);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        detailsRepository.deleteById(uuid);
        return new ApiResponse("Delete successfully",true);
    }
}
