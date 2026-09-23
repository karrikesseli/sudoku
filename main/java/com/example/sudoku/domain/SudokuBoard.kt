package com.example.sudoku.domain

data class SudokuBoard(
    val cells: List<SudokuCell>,
) {
    init {
        require(cells.size == SIZE * SIZE) { "A Sudoku board must contain 81 cells" }
    }

    operator fun get(row: Int, column: Int): SudokuCell = cells[index(row, column)]

    fun setValue(row: Int, column: Int, value: Int?): SudokuBoard {
        require(value == null || value in 1..9) { "Value must be between 1 and 9" }
        val cell = get(row, column)
        if (cell.isGiven) return this
        return copy(cells = cells.toMutableList().also {
            it[index(row, column)] = cell.copy(value = value)
        })
    }

    fun candidates(row: Int, column: Int): Set<Int> {
        if (get(row, column).isGiven) return emptySet()
        val used = buildSet {
            for (position in 0 until SIZE) {
                get(row, position).value?.let(::add)
                get(position, column).value?.let(::add)
            }
            val boxRow = row / BOX_SIZE * BOX_SIZE
            val boxColumn = column / BOX_SIZE * BOX_SIZE
            for (boxCellRow in boxRow until boxRow + BOX_SIZE) {
                for (boxCellColumn in boxColumn until boxColumn + BOX_SIZE) {
                    get(boxCellRow, boxCellColumn).value?.let(::add)
                }
            }
        }
        return (1..SIZE).toSet() - used
    }

    fun isComplete(): Boolean = cells.all { it.value != null } && isValid()

    fun isValid(): Boolean = (0 until SIZE).all { row ->
        valuesForRow(row).isUnique() && valuesForColumn(row).isUnique()
    } && (0 until SIZE step BOX_SIZE).all { row ->
        (0 until SIZE step BOX_SIZE).all { column -> valuesForBox(row, column).isUnique() }
    }

    private fun valuesForRow(row: Int) = (0 until SIZE).mapNotNull { get(row, it).value }
    private fun valuesForColumn(column: Int) = (0 until SIZE).mapNotNull { get(it, column).value }
    private fun valuesForBox(row: Int, column: Int) = buildList {
        for (boxRow in row until row + BOX_SIZE) {
            for (boxColumn in column until column + BOX_SIZE) add(get(boxRow, boxColumn).value)
        }
    }.filterNotNull()

    private fun List<Int>.isUnique() = size == toSet().size
    private fun index(row: Int, column: Int): Int {
        require(row in 0 until SIZE && column in 0 until SIZE)
        return row * SIZE + column
    }

    companion object {
        const val SIZE = 9
        private const val BOX_SIZE = 3

        fun empty() = SudokuBoard(List(SIZE * SIZE) { index ->
            SudokuCell(index / SIZE, index % SIZE)
        })
    }
}
