package com.example.Room.controller;

import com.example.Room.entity.Room;
import com.example.Room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Hiển thị danh sách phòng
    @GetMapping
    public String viewRoomList(Model model) {
        List<Room> listRooms = roomService.listAll();
        model.addAttribute("listRooms", listRooms);
        return "room_list"; // Tên của trang HTML để hiển thị danh sách phòng
    }

    // Hiển thị form tạo phòng mới
    @GetMapping("/new")
    public String showNewRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "new_room"; // Tên của trang HTML để tạo phòng mới
    }

    // Lưu phòng mới
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room,
                           @RequestParam("photoFile") MultipartFile photoFile) {
        if (!photoFile.isEmpty()) {
            try {
                room.setPhoto(photoFile.getBytes()); // Lưu hình ảnh vào thuộc tính photo
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý lỗi đọc file
            }
        }
        roomService.save(room);
        return "redirect:/rooms"; // Quay về danh sách phòng sau khi lưu
    }

    // Hiển thị form chỉnh sửa phòng
    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable("id") Long id, Model model) {
        Room room = roomService.get(id);
        model.addAttribute("room", room);
        return "edit_room"; // Tên của trang HTML để chỉnh sửa phòng
    }

    // Cập nhật phòng
    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable("id") Long id,
                             @ModelAttribute("room") Room room,
                             @RequestParam("photoFile") MultipartFile photoFile) {
        if (!photoFile.isEmpty()) {
            try {
                room.setPhoto(photoFile.getBytes()); // Cập nhật hình ảnh nếu có file mới
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý lỗi đọc file
            }
        }
        room.setRoomId(id); // Đặt ID của phòng trước khi lưu
        roomService.save(room);
        return "redirect:/rooms"; // Quay về danh sách phòng sau khi cập nhật
    }

    // Xóa phòng
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
        return "redirect:/rooms"; // Quay về danh sách phòng sau khi xóa
    }

    // Lấy hình ảnh của phòng
    @GetMapping("/photo/{id}")
    @ResponseBody
    public byte[] getRoomPhoto(@PathVariable Long id) {
        Room room = roomService.get(id);
        return room.getPhoto(); // Trả về hình ảnh dưới dạng byte[]
    }
}
