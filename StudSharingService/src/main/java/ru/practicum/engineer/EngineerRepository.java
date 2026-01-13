package ru.practicum.engineer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.engineer.model.Engineer;

@Repository
public interface EngineerRepository extends JpaRepository<Engineer, Long> {

}
