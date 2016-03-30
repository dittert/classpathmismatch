package tfo.ideabugs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DemoTest {

    @Before
    public void setup() {
        Spark.get("/hello", (req, res) -> "Hello world!");
        Spark.awaitInitialization();
    }

    @Test
    public void failsIfClasspathIsIncorrect() throws Exception {
        final URL url = new URL("http://localhost:4567/hello");
        final URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        assertThat(builder.toString(), equalTo("Hello world!"));
        System.out.println(builder.toString());
    }

    @Test
    public void locationShouldBePrinted() {
        System.out.println(HttpServletResponse.class.getProtectionDomain().getCodeSource().getLocation());
    }

    @After
    public void shutdownServer() {
        Spark.stop();
    }

    // for manual tests in browser
    public static void main(String[] args) throws Exception {
        final DemoTest instance = new DemoTest();
        instance.setup();
    }
}
