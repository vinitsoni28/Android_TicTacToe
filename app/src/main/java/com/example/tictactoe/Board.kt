package com.example.tictactoe

class Board {

    companion object {
        const val Player = "O"
        const val Computer = "X"
    }

    val board = Array(3) { arrayOfNulls<String>(3) }

    val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    val isGameOver: Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()

    fun hasComputerWon(): Boolean {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == Computer ||
            board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == Computer
        ) {
            return true
        }

        for (i in board.indices) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == Computer ||
                board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == Computer) {
                return true
            }
        }
        return false
    }

    fun hasPlayerWon(): Boolean {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == Player ||
            board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == Player
        ) {
            return true
        }

        for (i in board.indices) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == Player ||
                board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == Player) {
                return true
            }
        }
        return false
    }

    //Implementing MiniMax Algorithm
    var computersMove: Cell? = null

    fun minimax(depth: Int, player: String) : Int {
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return  -1
        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]

            if (player == Computer) {
                placeMove(cell, Computer)
                val currentScore = minimax(depth +1, Player)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                if (currentScore == 1) {
                    board[cell.i][cell.j] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }
            }
            else if (player == Player) {
                placeMove(cell, Player)
                val currentScore = minimax(depth + 1, Computer)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    board[cell.i][cell.j] = ""
                    break
                }
            }
            board[cell.i][cell.j] = ""
        }

        return if (player == Computer) max else min

    }

    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }

}