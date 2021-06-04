package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Client;
import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.LoginDto;
import ecma.ai.ussdapp.repository.ClientRepository;
import ecma.ai.ussdapp.repository.RoleRepository;
import ecma.ai.ussdapp.repository.SimcardRepository;
import ecma.ai.ussdapp.repository.StaffRepository;
import ecma.ai.ussdapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public ApiResponse loginForStaff(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            Staff staff = (Staff) authentication.getPrincipal();

            String token = jwtProvider.generateToken(loginDto.getUsername(), staff.getRoles());
            return new ApiResponse("Token", true, token);

        } catch (BadCredentialsException exception) {
            return new ApiResponse("Parol yoki username Xato", false);
        }
    }

    public ApiResponse loginForClient(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            Client client = (Client) authentication.getPrincipal();

            String token = jwtProvider.generateToken(loginDto.getUsername(), client.getRoles());
            return new ApiResponse("Token", true, token);

        } catch (BadCredentialsException exception) {
            return new ApiResponse("Parol yoki username Xato", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //jafar
        Optional<Staff> optionalStaff = staffRepository.findByUserName(username);


        //90 2455897
        Optional<SimCard> optionalSimCard = simcardRepository.findByCodeAndNumber(username.substring(0, 2), username.substring(3));

        if (optionalSimCard.isPresent()) return optionalSimCard.get();
        if (optionalStaff.isPresent()) return optionalStaff.get();
        throw new UsernameNotFoundException(username + "topilmadi!");
    }

    //unikal simcard number
    public UserDetails loadClientBySimCardNumber(String number) {

        SimCard simCard = simcardRepository.findBySimCardNumber(number)
                .orElseThrow(() -> new UsernameNotFoundException(number));

        //simcardni o'zi toliq
        return simCard;
    }

}
