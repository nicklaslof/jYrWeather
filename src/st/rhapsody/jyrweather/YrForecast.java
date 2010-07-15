package st.rhapsody.jyrweather;

import org.joda.time.DateTime;

public class YrForecast {

    private final YrRequest yrRequest;
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


    YrForecast(YrRequest yrRequest, String forecast, int symbol, float windDirectionDeg, String windDirectionCode, String windDirectionName, float windSpeed, String windName, String pressureUnit, Float pressureValue, float precipitation, int temperature,String temperatureUnit, DateTime validFrom, DateTime validTo) {
        this.yrRequest = yrRequest;
        this.forecast = forecast;
        this.symbol = symbol;
        this.windDirectionDeg = windDirectionDeg;
        this.windDirectionCode = windDirectionCode;
        this.windDirectionName = windDirectionName;
        this.windSpeed = windSpeed;
        this.windName = windName;
        this.pressureUnit = pressureUnit;
        this.pressureValue = pressureValue;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.validFrom = validFrom;
        this.validTo = validTo;
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

    public YrRequest getYrRequest() {
        return yrRequest;
    }

    @Override
    public String toString() {
        return "YrForecast{" + "yrRequest=" + yrRequest + "forecast=" + forecast + "symbol=" + symbol + "windDirectionDeg=" + windDirectionDeg + "windDirectionCode=" + windDirectionCode + "windDirectionName=" + windDirectionName + "windSpeed=" + windSpeed + "windName=" + windName + "pressureUnit=" + pressureUnit + "pressureValue=" + pressureValue + "precipitation=" + precipitation + "temperature=" + temperature + "temperatureUnit=" + temperatureUnit + "validFrom=" + validFrom + "validTo=" + validTo + '}';
    }
}
