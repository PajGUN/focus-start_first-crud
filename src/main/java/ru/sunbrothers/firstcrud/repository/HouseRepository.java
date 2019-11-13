package ru.sunbrothers.firstcrud.repository;


import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.sunbrothers.firstcrud.model.House;

import java.util.List;


public interface HouseRepository extends CrudRepository<House, Long> {
    List<House> findByBuildDate(@NonNull Integer buildDate);
}
