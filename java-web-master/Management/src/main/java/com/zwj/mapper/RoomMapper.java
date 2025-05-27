package com.zwj.mapper;

import com.zwj.pojo.Room;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomMapper {

    List<Room> getList();

}
