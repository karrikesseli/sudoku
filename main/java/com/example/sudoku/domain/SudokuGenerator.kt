package com.example.sudoku.domain

import kotlin.random.Random

class SudokuGenerator(private val random: Random = Random.Default) {
    fun generate(): SudokuBoard {
        val solved = SudokuSolver.solve(SudokuBoard.empty())
            ?: error("Could not generate a Sudoku solution")
        val cells = solved.cells.map { cell ->
            if (random.nextFloat() < 0.42f) cell else cell.copy(value = null, isGiven = false)
        }
        return SudokuBoard(cells.map { it.copy(isGiven = it.value != null) })
    }
}
