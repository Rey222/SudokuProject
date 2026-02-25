package com.example.sudokuproject

import kotlin.random.Random

class Solver {

    private val board = Array(9) { IntArray(9) }
    private val solution = Array(9) { IntArray(9) }
    private val isFixed = Array(9) { BooleanArray(9) }

    var selectedRow = -1
    var selectedColumn = -1
    var errorCount = 0
        private set

    fun generateBoard(difficulty: String) {
        val fullSolution = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        for (r in 0..8) {
            for (c in 0..8) {
                solution[r][c] = fullSolution[r][c]
                board[r][c] = fullSolution[r][c]
                isFixed[r][c] = true
            }
        }

        val cellsToRemove = when (difficulty) {
            "Легкий" -> 20
            "Средний" -> 35
            "Сложный" -> 50
            else -> 35
        }

        val random = Random
        var removed = 0
        while (removed < cellsToRemove) {
            val r = random.nextInt(9)
            val c = random.nextInt(9)
            if (board[r][c] != 0) {
                board[r][c] = 0
                isFixed[r][c] = false
                removed++
            }
        }
        errorCount = 0
    }

    fun setNumber(row: Int, col: Int, number: Int): Boolean {
        if (isFixed[row][col]) return false

        board[row][col] = number
        return board[row][col] == solution[row][col]
    }

    fun isSolved(): Boolean {
        for (r in 0..8) {
            for (c in 0..8) {
                if (board[r][c] != solution[r][c]) return false
            }
        }
        return true
    }

    fun getBoard() = board
    fun getSolution() = solution
    fun isFixed(row: Int, col: Int) = isFixed[row][col]

    fun resetBoard() {
        for (r in 0..8) {
            for (c in 0..8) {
                if (!isFixed[r][c]) {
                    board[r][c] = 0
                }
            }
        }
        errorCount = 0
    }
}