package com.example.sudoku.domain

object SudokuSolver {
    fun solve(board: SudokuBoard): SudokuBoard? {
        if (!board.isValid()) return null
        val nextCell = board.cells
            .filter { it.value == null }
            .minByOrNull { board.candidates(it.row, it.column).size }
            ?: return board

        for (candidate in board.candidates(nextCell.row, nextCell.column)) {
            solve(board.setValue(nextCell.row, nextCell.column, candidate))?.let { return it }
        }
        return null
    }
}
