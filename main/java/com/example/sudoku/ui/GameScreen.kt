package com.example.sudoku.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sudoku.ui.components.NumberPad
import com.example.sudoku.ui.components.SudokuBoardView

@Composable
fun GameScreen(viewModel: GameViewModel, modifier: Modifier = Modifier) {
    val board by viewModel.board.collectAsState()
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text("Sudoku", style = MaterialTheme.typography.headlineMedium)
        SudokuBoardView(board, selectedCell, { row, column -> selectedCell = row to column })
        NumberPad({ value -> selectedCell?.let { viewModel.selectNumber(it.first, it.second, value) } })
        Button(onClick = viewModel::newGame) { Text("New game") }
        if (board.isComplete()) Text("Puzzle complete", color = MaterialTheme.colorScheme.primary)
    }
}
