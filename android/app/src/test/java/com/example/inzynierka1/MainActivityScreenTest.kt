//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.inzynierka1.ui.screens.MainActivityScreen
//import com.example.inzynierka1.uiState.UserState
//import com.example.inzynierka1.uiState.MainUiState
//import com.example.inzynierka1.viewmodels.MainViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.flowOf
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.junit.MockitoJUnitRunner
//
//
//@RunWith(MockitoJUnitRunner::class)
//class MainActivityScreenTest {
//
//    @Mock
//    private lateinit var viewModel: MainViewModel
//
//    @Composable
//    @Test
//    fun `test if MainActivityScreen correctly renders image and button based on ViewModel state`() {
//        // Given
//        val expectedUiState = MainUiState(message = "Test Message", userState = UserState.STANDING, button = "Test Button")
//        `when`(viewModel.uiState).thenReturn(flowOf(expectedUiState) as StateFlow<MainUiState>?)
//
//        // When
//        val actualComposable = MainActivityScreen(viewModel = viewModel)
//
//        // Then
//        assertEquals(expectedUiState.message, actualComposable.MainUiState.message)
//        assertEquals(expectedUiState.userState, actualComposable.uiState.userState)
//        assertEquals(expectedUiState.button, actualComposable.uiState.button)
//    }
//}
