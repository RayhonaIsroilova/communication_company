package ecma.ai.ussdapp.component;

import ecma.ai.ussdapp.entity.*;
import ecma.ai.ussdapp.entity.enums.ClientType;
import ecma.ai.ussdapp.entity.enums.RoleName;
import ecma.ai.ussdapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    USSDRepository ussdRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            Staff director = new Staff();
            director.setUserName("Jafarbek");
            director.setFullName("TJU");
            director.setPosition("BOSS");
            director.setPassword(passwordEncoder.encode("123"));
            director.setEnabled(true); //tizimdan foydalanish

            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            director.setRoles(roles);
            staffRepository.save(director);


            Client client = new Client();
            client.setClientType(ClientType.JISMONIY);
            client.setPassword(passwordEncoder.encode("123"));
            client.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_CLIENT)));
            client.setFullName("To'rayev Avazbek");
            client.setPassportNumber("AA4444206");

            clientRepository.save(client);


            List<UssdCode> ussdCodes = new ArrayList<>();
            UssdCode hisob = new UssdCode();

            hisob.setCode("*100#");
            hisob.setDescription("O'sha hisobni tekshirish!");

            ussdCodes.add(hisob);

            ussdRepository.saveAll(ussdCodes);

            Tariff tariff = new Tariff();
            tariff.setId(UUID.fromString("19722989-39d2-425c-b52d-a32b606a7703"));
            tariff.setSwitchPrice(2500);
            tariff.setPrice(70000);
            tariff.setName("Bir oy");
            tariff.setExpireDate(new Timestamp(System.currentTimeMillis() + 30L * 1000 * 60 * 60 * 24));
            tariff.setMb(1000);
            tariff.setMin(1000);
            tariff.setSms(1000);

            tariff.setMbCost(25);
            tariff.setMinCost(25);
            tariff.setSmsCost(25);

            tariffRepository.save(tariff);

            SimCard simCard = new SimCard();
            simCard.setBalance(0);
            simCard.setActive(false);
            simCard.setCode("91");
            simCard.setSimCardNumber("2455897");
            simCard.setNumber("2455897");
            simCard.setName("Beeline");

            simcardRepository.save(simCard);
        }
    }
}
