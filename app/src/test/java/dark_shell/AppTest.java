package dark_shell;

import static org.junit.jupiter.api.Assertions.*;

import dark_shell.utils.Constants;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void appHasAMain() {
        App classUnderTest = new App();
        Method[] methods = classUnderTest.getClass().getMethods();
        List<String> methodsNames =
                Arrays.stream(methods).map(method -> method.getName()).toList();
        assertTrue(methodsNames.contains(Constants.APP_TEST_MAIN_METHOD_NAME), Constants.APP_TEST_MESSAGE);
    }
}
