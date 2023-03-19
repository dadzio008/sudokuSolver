package com.example.sudokusolver.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Figure {
    int x;
    int y;
    int value;
}
