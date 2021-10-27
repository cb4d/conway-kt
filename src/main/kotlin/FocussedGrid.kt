@JvmInline
value class Focus constructor(val point: Pair<Int, Int>)
val Focus.x
    get() = point.first
val Focus.y
    get() = point.second

data class FocussedGrid<A>(
    val focus: Focus,
    val grid: List<List<A>>
) {
    companion object {
        // functor
        fun <A, B> map(fa: FocussedGrid<A>, f: (A) -> B): FocussedGrid<B> {
            return FocussedGrid(
                fa.focus,
                fa.grid.map { it.map(f) }
            )
        }

        // comonad
        fun <A> extract(fa: FocussedGrid<A>): A {
            return fa.focus.let { fa.grid[it.x][it.y] }
        }

        fun <A, B> coflatMap(fa: FocussedGrid<A>, f: (FocussedGrid<A>) -> B): FocussedGrid<B> {
            // a grid of FocussedGrid<A>s, where each point has grid == fa.grid and focus focus at that point
            val grid = fa.grid.mapIndexed { x, row ->
                row.mapIndexed { y, _ ->
                    FocussedGrid(
                        Focus(x to y),
                        fa.grid
                    )
                }
            }

            return map(FocussedGrid(fa.focus, grid), f)
        }
    }
}
