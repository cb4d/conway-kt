import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class ViewModel {
    private val startTapCount = MutableStateFlow(0)

    val gameOfLife =
        startTapCount.flatMapLatest {
            if (it == 0) {
                return@flatMapLatest emptyFlow()
            }

            flow {
                var focussedGrid = FocussedGrid(Focus(0 to 0), initialGrid())

                while (true) {
                    delay(TICK_MS)
                    emit(focussedGrid.grid)
                    focussedGrid = nextGrid(focussedGrid)
                }
            }
        }

    fun startTapped() {
        startTapCount.value = startTapCount.value + 1
    }

    companion object {
        private const val TICK_MS = 200L
        const val GRID_SIZE = 150
        const val GRID_WIDTH = 500
        val LIFE_GENERATOR = { Random.nextInt() % 2 == 0 }
    }
}