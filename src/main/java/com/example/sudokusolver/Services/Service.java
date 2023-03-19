package com.example.sudokusolver.Services;

import com.example.sudokusolver.Models.Figure;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    List resultList = new ArrayList<>();


    public void solveSudoku(List<Figure> listOfFigures) {
        System.out.println(listOfFigures);
        solve(listOfFigures, 0);
        System.out.println(listOfFigures);
        resultList = listOfFigures;
    }

    private boolean solve(List<Figure> puzzle, int index) {
        if (index == puzzle.size()) {
            return true;
        }

        Figure currentFigure = puzzle.get(index);
        if (currentFigure.getValue() != 0) {
            return solve(puzzle, index + 1);
        }
        for (int i = 1; i <= 9; i++) {
            currentFigure.setValue(i);
            if (isValid(puzzle, currentFigure)) {
                if (solve(puzzle, index + 1)) {
                    return true;
                }
            }
        }
        currentFigure.setValue(0);
        return false;
    }

    private boolean isValid(List<Figure> puzzle, Figure figure) {
        int row = figure.getX();
        int col = figure.getY();
        int value = figure.getValue();

        for (int i = 0; i < 9; i++) {
            int index = row * 9 + i;
            if (index >= puzzle.size()) {
                break;
            }
            Figure currentFigure = puzzle.get(index);
            if (currentFigure.getValue() == value && currentFigure.getY() != col) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            int index = i * 9 + col;
            if (index >= puzzle.size()) {
                break;
            }
            Figure currentFigure = puzzle.get(index);
            if (currentFigure.getValue() == value && currentFigure.getX() != row) {
                return false;
            }
        }
        int boxRow = row / 3 * 3;
        int boxCol = col / 3 * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                int index = i * 9 + j;
                if (index >= puzzle.size()) {
                    break;
                }
                Figure currentFigure = puzzle.get(index);
                if (currentFigure.getValue() == value && currentFigure.getX() != row && currentFigure.getY() != col) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Figure> getResultList() {
        return resultList;
    }

    @Component
    public class CorsFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                        final FilterChain filterChain) throws ServletException, IOException {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addIntHeader("Access-Control-Max-Age", 10);
            filterChain.doFilter(request, response);
        }
    }
}
