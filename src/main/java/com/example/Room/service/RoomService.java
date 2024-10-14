package com.example.Room.service;

import com.example.Room.entity.Room;
import com.example.Room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private  RoomRepository roomRepository;

    public List<Room> listAll() {
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null); // Lấy phòng theo ID
    }


    public void deleteRoom(Long id) {
        roomRepository.deleteById(id); // Xóa phòng theo ID
    }

}
