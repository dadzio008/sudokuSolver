package com.example.sudokusolver.Controllers;

import com.example.sudokusolver.Models.Figure;
import com.example.sudokusolver.Services.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class SudokuController {

    private Service service;

    public SudokuController(Service service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<List<Figure>> sendData(@RequestBody List<Figure> list) {
        this.service.solveSudoku(list);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("HomePage");
        return modelAndView;
    }

    @GetMapping("/result")
    public List<Figure> result(){
        return this.service.getResultList();
    }
}