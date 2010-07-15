/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st.rhapsody.jyrweather;

import java.util.ArrayList;
import java.util.List;
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

    YrResult(DateTime sunrise, DateTime sunset, String creditLinkText, String creditLinkUrl, DateTime lastUpdate, DateTime nextUpdate) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.creditLinkText = creditLinkText;
        this.creditLinkUrl = creditLinkUrl;
        this.lastUpdate = lastUpdate;
        this.nextUpdate = nextUpdate;
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

    public void addYrForecast(YrForecast yrForecast){
        this.forecasts.add(yrForecast);
    }

    @Override
    public String toString() {
        return "YrResult{" + "forecasts=" + forecasts + "sunrise=" + sunrise + "sunset=" + sunset + "creditLinkText=" + creditLinkText + "creditLinkUrl=" + creditLinkUrl + "lastUpdate=" + lastUpdate + "nextUpdate=" + nextUpdate + '}';
    }

    
}
