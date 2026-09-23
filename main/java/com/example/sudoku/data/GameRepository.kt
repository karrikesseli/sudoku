package com.example.sudoku.data

import com.example.sudoku.domain.SudokuBoard
import com.example.sudoku.domain.SudokuGenerator

class GameRepository(private val generator: SudokuGenerator = SudokuGenerator()) {
    private var currentBoard: SudokuBoard = generator.generate()

    fun loadGame(): SudokuBoard = currentBoard

    fun saveGame(board: SudokuBoard) {
        currentBoard = board
    }

    fun newGame(): SudokuBoard {
        currentBoard = generator.generate()
        return currentBoard
    }
}
