package ru.yandex.practicum.controler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.Mpa;
import ru.yandex.practicum.service.MpaService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;


    @GetMapping
    public List<Mpa> findAll() {
        return mpaService.findAll();
    }


    @GetMapping("/{id}")
    public Mpa getMpaRating(@PathVariable("id") int mpaId) {
        return mpaService.getMpaRating(mpaId);
    }
}