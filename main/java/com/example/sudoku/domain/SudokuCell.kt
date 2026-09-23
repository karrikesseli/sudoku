package com.example.sudoku.domain

data class SudokuCell(
    val row: Int,
    val column: Int,
    val value: Int? = null,
    val isGiven: Boolean = false,
) {
    init {
        require(row in 0..8) { "Row must be between 0 and 8" }
        require(column in 0..8) { "Column must be between 0 and 8" }
        require(value == null || value in 1..9) { "Value must be between 1 and 9" }
    }
}
