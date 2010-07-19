package st.rhapsody.jyrweather;

import org.jdom.Element;
import org.joda.time.DateTime;

public class YrForecast {


    private final String forecast;
    private final int symbol;
    private final float windDirectionDeg;
    private final String windDirectionCode;
    private final String windDirectionName;
    private final float windSpeed;
    private final String windName;
    private final String pressureUnit;
    private final float pressureValue;
    private final float precipitation;
    private final int temperature;
    private final String temperatureUnit;
    private final DateTime validFrom;
    private final DateTime validTo;

    YrForecast(Element weatherdata) {
        validFrom = new DateTime(weatherdata.getAttributeValue("from"));
        validTo = new DateTime(weatherdata.getAttributeValue("to"));
        forecast = YrRequestProcessor.getXMLValue(weatherdata, "name", "symbol");
        symbol = Integer.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "number", "symbol"));
        windDirectionDeg = Float.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "deg", "windDirection"));
        windDirectionCode = YrRequestProcessor.getXMLValue(weatherdata, "code", "windDirection");
        windDirectionName = YrRequestProcessor.getXMLValue(weatherdata, "name", "windDirection");
        windSpeed = Float.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "mps", "windSpeed"));
        windName = YrRequestProcessor.getXMLValue(weatherdata, "name", "windSpeed");
        temperatureUnit = YrRequestProcessor.getXMLValue(weatherdata, "unit", "temperature");
        temperature = Integer.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "value", "temperature"));
        pressureUnit = YrRequestProcessor.getXMLValue(weatherdata, "unit", "pressure");
        pressureValue = Float.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "value", "pressure"));
        precipitation = Float.valueOf(YrRequestProcessor.getXMLValue(weatherdata, "value", "precipitation"));
    }

    public String getForecast() {
        return forecast;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }

    public Float getPressureValue() {
        return pressureValue;
    }

    public int getSymbol() {
        return symbol;
    }

    public int getTemperature() {
        return temperature;
    }

    public DateTime getValidFrom() {
        return validFrom;
    }

    public DateTime getValidTo() {
        return validTo;
    }

    public String getWindDirectionCode() {
        return windDirectionCode;
    }

    public float getWindDirectionDeg() {
        return windDirectionDeg;
    }

    public String getWindDirectionName() {
        return windDirectionName;
    }

    public String getWindName() {
        return windName;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "YrForecast{" + "forecast=" + forecast + "symbol=" + symbol + "windDirectionDeg=" + windDirectionDeg + "windDirectionCode=" + windDirectionCode + "windDirectionName=" + windDirectionName + "windSpeed=" + windSpeed + "windName=" + windName + "pressureUnit=" + pressureUnit + "pressureValue=" + pressureValue + "precipitation=" + precipitation + "temperature=" + temperature + "temperatureUnit=" + temperatureUnit + "validFrom=" + validFrom + "validTo=" + validTo + '}';
    }
}
