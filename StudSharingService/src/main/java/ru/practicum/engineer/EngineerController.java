package ru.practicum.engineer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EngineerController {

    private final EngineerService engineerService;

    @GetMapping("/api/engineers")
    public ResponseEntity<Collection<EngineerDto>> getAllEngineers() {
        log.info("Получен запрос на получение всех инженеров");
        return ResponseEntity.status(HttpStatus.OK).body(engineerService.getAllEngineers());
    }

    @GetMapping("/api/engineers/{id}")
    public ResponseEntity<EngineerDto> getEngineerById(@PathVariable Long id) {
        log.info("Получен запрос на получение инженера по id = {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(engineerService.getEngineerById(id));
    }

    @PostMapping("/api/admin/engineers")
    public ResponseEntity<EngineerDto> createEngineer(@RequestBody EngineerDto engineerDto) {
        log.info("Получен запрос от администратора на создание инженера");
        EngineerDto createdEngineer = engineerService.createEngineer(engineerDto);
        log.info("Инженер с id={} создан", createdEngineer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEngineer);
    }

    @PutMapping("/api/admin/engineers/{id}")
    public ResponseEntity<EngineerDto> updateEngineer(@PathVariable Long id, @RequestBody EngineerDto engineerDto) {
        log.info("Получен запрос от администратора на обновление инженера");
        EngineerDto updatedEngineerDto = engineerService.editEngineer(id, engineerDto);
        log.info("Инженер с id={} обновлен", id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEngineerDto);
    }

    @DeleteMapping("/api/admin/engineers/{id}")
    public ResponseEntity<Void> deleteEngineer(@PathVariable Long id) {
        log.info("Запрос от администратора на удаление инженера по id={}", id);
        engineerService.deleteEngineer(id);
        log.info("Инженер с id={} удален", id);
        return ResponseEntity.noContent().build();
    }


}
