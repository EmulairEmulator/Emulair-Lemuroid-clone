import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterTest {

    @Mock
    private MyView mockView;

    private MyPresenter presenter;

    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MyPresenter(mockView);
    }

    @Test
    public void fetchData_Success() {
        // Setup
        setup();
        when(mockView.isNetworkAvailable()).thenReturn(true);

        // Action
        presenter.fetchData();

        // Verification
        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showData();
    }

    @Test
    public void fetchData_NoNetwork() {
        // Setup
        setup();
        when(mockView.isNetworkAvailable()).thenReturn(false);

        // Action
        presenter.fetchData();

        // Verification
        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showNoNetworkError();
    }
}
