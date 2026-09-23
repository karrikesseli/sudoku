package com.example.sudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sudoku.domain.SudokuBoard

@Composable
fun SudokuBoardView(
    board: SudokuBoard,
    selectedCell: Pair<Int, Int>?,
    onCellSelected: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier.fillMaxWidth().aspectRatio(1f).border(2.dp, MaterialTheme.colorScheme.onSurface),
    ) {
        for (row in 0 until SudokuBoard.SIZE) {
            Row(Modifier.weight(1f)) {
                for (column in 0 until SudokuBoard.SIZE) {
                    val cell = board[row, column]
                    val selected = selectedCell == (row to column)
                    Box(
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                            .border(0.5.dp, MaterialTheme.colorScheme.outline)
                            .clickable { onCellSelected(row, column) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = cell.value?.toString().orEmpty(),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (cell.isGiven) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}
