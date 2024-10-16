package com.example.Room.service;

import com.example.Room.entity.Room;
import com.example.Room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> listAll() {
        return roomRepository.findAll();
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room get(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
