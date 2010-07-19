/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st.rhapsody.jyrweather;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;
import org.joda.time.DateTime;

/**
 *
 * @author nicklas
 */
public class YrResult {

    private final List<YrForecast> forecasts = new ArrayList<YrForecast>();
    private final DateTime sunrise;
    private final DateTime sunset;
    private final String creditLinkText;
    private final String creditLinkUrl;
    private final DateTime lastUpdate;
    private final DateTime nextUpdate;

    YrResult(Element weatherdata) {

        sunrise = new DateTime(YrRequestProcessor.getXMLValue(weatherdata, "rise", "sun"));
        sunset = new DateTime(YrRequestProcessor.getXMLValue(weatherdata, "set", "sun"));

        creditLinkText = YrRequestProcessor.getXMLValue(weatherdata, "text", "credit", "link");
        creditLinkUrl = YrRequestProcessor.getXMLValue(weatherdata, "url", "credit", "link");

        lastUpdate = new DateTime(weatherdata.getChild("meta").getChild("lastupdate").getText());
        nextUpdate = new DateTime(weatherdata.getChild("meta").getChild("nextupdate").getText());
    }

    public String getCreditLinkText() {
        return creditLinkText;
    }

    public String getCreditLinkUrl() {
        return creditLinkUrl;
    }

    public List<YrForecast> getForecasts() {
        return forecasts;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public DateTime getNextUpdate() {
        return nextUpdate;
    }

    public DateTime getSunrise() {
        return sunrise;
    }

    public DateTime getSunset() {
        return sunset;
    }

    public void addYrForecast(YrForecast yrForecast) {
        this.forecasts.add(yrForecast);
    }

    @Override
    public String toString() {
        return "YrResult{" + "forecasts=" + forecasts + "sunrise=" + sunrise + "sunset=" + sunset + "creditLinkText=" + creditLinkText + "creditLinkUrl=" + creditLinkUrl + "lastUpdate=" + lastUpdate + "nextUpdate=" + nextUpdate + '}';
    }
}
