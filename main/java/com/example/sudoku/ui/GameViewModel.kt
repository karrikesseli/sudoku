package com.example.sudoku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sudoku.data.GameRepository
import com.example.sudoku.domain.SudokuBoard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {
    private val _board = MutableStateFlow(repository.loadGame())
    val board: StateFlow<SudokuBoard> = _board.asStateFlow()

    fun selectNumber(row: Int, column: Int, value: Int?) {
        val updated = _board.value.setValue(row, column, value)
        if (updated.isValid()) {
            _board.value = updated
            viewModelScope.launch { repository.saveGame(updated) }
        }
    }

    fun newGame() {
        val newBoard = repository.newGame()
        _board.value = newBoard
    }

    fun solve() {
        val solved = com.example.sudoku.domain.SudokuSolver.solve(_board.value) ?: return
        _board.value = solved
        viewModelScope.launch { repository.saveGame(solved) }
    }

    class Factory(private val repository: GameRepository = GameRepository()) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(GameViewModel::class.java))
            return GameViewModel(repository) as T
        }
    }
}
