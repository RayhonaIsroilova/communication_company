package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.SimCardDto;
import ecma.ai.ussdapp.repository.SimcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimCardService {

    @Autowired
    SimcardRepository simcardRepository;


    public ApiResponse AddSimCard(SimCardDto simcardDto) {
        if (!simcardDto.getCode().startsWith("+998") && simcardDto.getCode().length() != 6) {
            return new ApiResponse("xatolik. UZB nomer emas bu", false);
        }
        if (simcardDto.getNumber().length() != 7)
            return new ApiResponse("nomerda raqamlar kam !!!", false);

        if (simcardRepository.findByCodeAndNumber(simcardDto.getCode(), simcardDto.getNumber()).isPresent()) {
            return new ApiResponse("bunday nomer mavjud", false);
        }
        if (simcardRepository.findBySimCardNumber(simcardDto.getSimCardNumber()).isPresent()) {
            return new ApiResponse("bunday simcard mavjud", false);
        }


        SimCard simCard = new SimCard();
        simCard.setSimCardNumber(simcardDto.getSimCardNumber());
        simCard.setCode(simcardDto.getCode());
        simCard.setPinCode(simcardDto.getPinCode());
        simCard.setNumber(simcardDto.getNumber());
        simCard.setName(simcardDto.getName());
        simcardRepository.save(simCard);
        return new ApiResponse("simcard added", true);
    }


    public ApiResponse editSimCard(SimCardDto simcardDto,UUID id) {
        Optional<SimCard> byId = simcardRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This id not found", false);
        if (!simcardDto.getCode().startsWith("+998") && simcardDto.getCode().length() != 6) {
            return new ApiResponse("xatolik. UZB nomer emas bu", false);
        }
        if (simcardDto.getNumber().length() != 7)
            return new ApiResponse("nomerda raqamlar kam !!!", false);

        if (simcardRepository.findByCodeAndNumber(simcardDto.getCode(), simcardDto.getNumber()).isPresent()) {
            return new ApiResponse("bunday nomer mavjud", false);
        }
        if (simcardRepository.findBySimCardNumber(simcardDto.getSimCardNumber()).isPresent()) {
            return new ApiResponse("bunday simcard mavjud", false);
        }


        SimCard simCard = byId.get();
        simCard.setSimCardNumber(simcardDto.getSimCardNumber());
        simCard.setCode(simcardDto.getCode());
        simCard.setPinCode(simcardDto.getPinCode());
        simCard.setNumber(simcardDto.getNumber());
        simCard.setName(simcardDto.getName());
        simcardRepository.save(simCard);
        return new ApiResponse("simCard edited", true);
    }


    public ApiResponse getSimCardById(UUID id) {
        Optional<SimCard> byId = simcardRepository.findById(id);
        return byId.map(simCard -> new ApiResponse("mana ol", true, simCard)).orElseGet(() -> new ApiResponse("This id not found", false));
    }


    public List<SimCard> getSimCardList() {
        return simcardRepository.findAll();
    }


    public ApiResponse delete(UUID id) {
        Optional<SimCard> byId = simcardRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This id not found", false);
        simcardRepository.deleteById(id);
        return new ApiResponse("delete success", true);
    }
}
