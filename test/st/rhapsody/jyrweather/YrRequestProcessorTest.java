package st.rhapsody.jyrweather;

import java.io.IOException;
import java.net.MalformedURLException;
import org.jdom.JDOMException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class YrRequestProcessorTest {

    public YrRequestProcessorTest() {
    }

    @Test
    public void basicresulttestwithdevurl() throws MalformedURLException, IOException, JDOMException {


        // Remove the URL string argument to send the query to yr.no servers.

        YrRequest yrRequest = new YrRequest("Sweden", "Stockholm", "Stockholm", 1, "http://local_copy_of_forecast_xml/forecast.xml");
        YrResult result = YrRequestProcessor.getInstance().getResult(yrRequest);
        for (YrForecast yrForecast : result.getForecasts()) {
            System.out.println(yrForecast);
        }

        assertNotNull(result.getCreditLinkText());
        assertNotNull(result.getForecasts().get(0).getForecast());

    }
}
