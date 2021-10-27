import Cell.*

enum class Cell {
    DEAD, ALIVE
}

fun FocussedGrid<Cell>.localSum(): Int {
    val (x, y) = focus.point

    return listOf(
        (x - 1 to y),
        (x + 1 to y),
        (x to y - 1),
        (x to y + 1)
    ).fold(0) { acc, (nx, ny) ->
        acc + (grid.getOrNull(nx)?.getOrNull(ny)?.ordinal ?: 0)
    }
}

fun conwayStep(grid: FocussedGrid<Cell>): Cell {
    val liveNeighbours = grid.localSum()

    return when (FocussedGrid.extract(grid)) {
        ALIVE -> if (liveNeighbours in 2..3) ALIVE else DEAD
        DEAD -> if (liveNeighbours == 3) ALIVE else DEAD
    }
}

fun nextGrid(grid: FocussedGrid<Cell>): FocussedGrid<Cell> =
    FocussedGrid.coflatMap(grid, ::conwayStep)
