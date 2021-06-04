package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Tariff;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.TariffDTO;
import ecma.ai.ussdapp.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TariffService {
    @Autowired
    TariffRepository tariffRepository;

    public List<Tariff> getTariffAll() {
        return tariffRepository.findAll();
    }

    public ApiResponse getTariffById(UUID uuid) {
        Optional<Tariff> byId = tariffRepository.findById(uuid);
        return byId.map(tariff -> new ApiResponse("Mana ol", true, tariff)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addTariff(TariffDTO tariffDTO) {
        if (tariffRepository.existsByName(tariffDTO.getName()))
            return new ApiResponse("bunaqa tariff bor", false);
        Tariff tariff = new Tariff();
        tariff.setMb(tariffDTO.getMb());
        tariff.setExpireDate(tariffDTO.getExpireDate());
        tariff.setMbCost(tariffDTO.getMbCost());
        tariff.setMin(tariffDTO.getMin());
        tariff.setName(tariffDTO.getName());
        tariff.setPrice(tariffDTO.getPrice());
        tariff.setMinCost(tariffDTO.getMinCost());
        tariff.setSms(tariffDTO.getSms());
        tariff.setSmsCost(tariffDTO.getSmsCost());
        tariff.setSwitchPrice(tariffDTO.getSwitchPrice());
        tariffRepository.save(tariff);
        return new ApiResponse("Saved successfully", true, tariff);
    }


    public ApiResponse editTariff(UUID uuid, TariffDTO tariffDTO) {
        Optional<Tariff> byId = tariffRepository.findById(uuid);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        if (tariffRepository.existsByName(tariffDTO.getName()))
            return new ApiResponse("bunaqa nomli tariff bor", false);
        Tariff tariff = byId.get();
        tariff.setMb(tariffDTO.getMb());
        tariff.setExpireDate(tariffDTO.getExpireDate());
        tariff.setMbCost(tariffDTO.getMbCost());
        tariff.setMin(tariffDTO.getMin());
        tariff.setName(tariffDTO.getName());
        tariff.setPrice(tariffDTO.getPrice());
        tariff.setMinCost(tariffDTO.getMinCost());
        tariff.setSms(tariffDTO.getSms());
        tariff.setSmsCost(tariffDTO.getSmsCost());
        tariff.setSwitchPrice(tariffDTO.getSwitchPrice());
        tariffRepository.save(tariff);
        return new ApiResponse("Saved successfully", true, tariff);
    }


    public ApiResponse delete(UUID uuid) {
        Optional<Tariff> byId = tariffRepository.findById(uuid);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        tariffRepository.deleteById(uuid);
        return new ApiResponse("Delete successfully", true);
    }
}
