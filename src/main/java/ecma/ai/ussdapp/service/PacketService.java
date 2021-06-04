package ecma.ai.ussdapp.service;

import ecma.ai.ussdapp.entity.Packet;
import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.enums.PacketType;
import ecma.ai.ussdapp.payload.ApiResponse;
import ecma.ai.ussdapp.payload.PacketDTO;
import ecma.ai.ussdapp.payload.PacketDTOClient;
import ecma.ai.ussdapp.repository.PacketRepository;
import ecma.ai.ussdapp.repository.SimcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacketService {

    @Autowired
    PacketRepository packetRepository;

    @Autowired
    SimcardRepository simcardRepository;

    public List<Packet> getPacketAll(){
        return packetRepository.findAll();
    }

    public ApiResponse getPacketById(UUID uuid){
        Optional<Packet> byId = packetRepository.findById(uuid);
        return byId.map(packet -> new ApiResponse("Mana ol", true, packet)).orElseGet(() -> new ApiResponse("This id not found", false));
    }


    public ApiResponse addPacket(PacketDTO packetDTO){
        boolean b = packetRepository.existsByName(packetDTO.getName());
        if (b) return new ApiResponse("Bunaqa packet bor !!!!!!",false);
        Packet packet = new Packet();
        packet.setName(packetDTO.getName());
        if (packetDTO.getPacketTypeId()==1){
            packet.setPacketType(PacketType.MB);
        }else if (packetDTO.getPacketTypeId()==2){
            packet.setPacketType(PacketType.SMS);
        }else if (packetDTO.getPacketTypeId()==3){
            packet.setPacketType(PacketType.MIN);
        }else {
            return new ApiResponse("Bunaqa packet turi yo'q adashding",false);
        }
        packet.setAmount(packetDTO.getAmount());
        packet.setDuration(packetDTO.getDuration());
        packet.setPrice(packetDTO.getPrice());
        packet.setExpireDate(packetDTO.getExpireDate());
        List<SimCard> simCardList2 = new ArrayList<>();
        List<SimCard> simCardList = packetDTO.getSimCardList();
        for (SimCard simCard : simCardList) {
            Optional<SimCard> bySimCardNumber = simcardRepository.findBySimCardNumber(simCard.getSimCardNumber());
            simCardList2.add(bySimCardNumber.orElseThrow());
        }
        packet.setSimCardList(simCardList2);
        packetRepository.save(packet);
        return new ApiResponse("Successfully saved",true,packet);
    }

    public ApiResponse editPacket(PacketDTOClient packetDTO,UUID uuid){
        Optional<Packet> byId = packetRepository.findById(uuid);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Packet packet = byId.get();
        packet.setPacketType(packetDTO.getPacketType());
        packet.setAmount(packetDTO.getAmount());
        packet.setName(packetDTO.getName());
        packet.setDuration(packetDTO.getDuration());
        packet.setPrice(packetDTO.getPrice());
        packet.setExpireDate(packetDTO.getExpireDate());
        packetRepository.save(packet);
        return  new ApiResponse("Edited successfully",true,packet);
    }

    public ApiResponse delete(UUID uuid){
        Optional<Packet> byId = packetRepository.findById(uuid);
        if (!byId.isPresent())
            return new ApiResponse("This id not found bro",false);
        packetRepository.deleteById(uuid);
        return new ApiResponse("Delete success",true);
    }
}
