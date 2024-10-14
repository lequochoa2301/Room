package com.example.Room.controller;

import com.example.Room.entity.Room;
import com.example.Room.entity.User;
import com.example.Room.repository.RoomRepository;
import com.example.Room.repository.UserRepository;
import com.example.Room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class RoomController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/")
    public String viewHomePage(Model model) {

        if(!userRepository.existsById(2L)){
            for(int i = 2; i <5; i++){
                User user = new User();
                user.setEnabled(true);
                user.setUsername("loan"+i);
                userRepository.save(user);
            }
            User user = new User();
            user.setEnabled(true);
            user.setUsername("loan");
            //user.setId(1L);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("123456");
            user.setPassword(encodedPassword);
        }


        List<Room> roomList = roomService.listAll();
        model.addAttribute("roomList", roomList);

        return "home";
    }

    @GetMapping("/new")
    public String showRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "newRoom";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id); // Lấy phòng theo ID để chỉnh sửa
        model.addAttribute("room", room);
        return "update";
    }
    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room) {
        room.setRoomId(id);
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}
