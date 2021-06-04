package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Client;
import ecma.ai.ussdapp.entity.Role;
import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.Tariff;
import ecma.ai.ussdapp.entity.enums.ClientType;
import ecma.ai.ussdapp.entity.enums.RoleName;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.ClientDto;
import ecma.ai.ussdapp.repository.ClientRepository;
import ecma.ai.ussdapp.repository.RoleRepository;
import ecma.ai.ussdapp.repository.SimcardRepository;
import ecma.ai.ussdapp.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public ApiResponse buySimcard(ClientDto clientDto) {

        boolean existsByPassportNumber = clientRepository.existsByPassportNumber(clientDto.getPassportNumber());

        Client client = new Client();
        if (existsByPassportNumber) {

            Optional<Client> byPassportNumber = clientRepository.findByPassportNumber(clientDto.getPassportNumber());

            client = byPassportNumber.get();

        } else {
            //mijoz 1-marta

            client.setPassportNumber(clientDto.getPassportNumber());
            if (clientDto.getClientTypeOrdinal() == 1) {
                client.setClientType(ClientType.JISMONIY);
            } else if (clientDto.getClientTypeOrdinal() == 2) {
                client.setClientType(ClientType.YURIDIK);
            } else {
                return new ApiResponse("Client type notogri keldi!", false);
            }

            client.setFullName(clientDto.getFullName());

            Role byRoleName = roleRepository.findByRoleName(RoleName.ROLE_CLIENT);
            client.setRoles(Collections.singleton(byRoleName));

        }

        Optional<Tariff> optionalTariff = tariffRepository.findById(clientDto.getBuyingSimCardDto().getTariffId());

        if (!optionalTariff.isPresent()) return new ApiResponse("Tariff not found!", false);
        Tariff tariff = optionalTariff.get();

        Optional<SimCard> optionalSimCard = simcardRepository.findByCodeAndNumber(clientDto.getBuyingSimCardDto().getCode(), clientDto.getBuyingSimCardDto().getNumber());

        if (!optionalSimCard.isPresent()) return new ApiResponse("Simcard not found!", false);
        SimCard simCard = optionalSimCard.get();

        //sim karta aktiv bo'lmasligi kk
        if (simCard.isActive()) return new ApiResponse("Simcard already bought", false);

        simCard.setActive(true);
        simCard.setTariff(tariff);
        simCard.setClient(client);
        simCard.setBalance(clientDto.getBuyingSimCardDto().getSum());


        simcardRepository.save(simCard);
        //mana shu joyda simcardga min sms mb qancha borligini field
        clientRepository.save(client);
//        client.setSimCardList(Collections.singletonList(simCard));
//        clientRepository.save(client);
        return new ApiResponse("simcard rasmiylashdi!", true);

    }
}
