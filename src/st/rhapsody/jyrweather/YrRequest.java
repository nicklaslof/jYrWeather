package st.rhapsody.jyrweather;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

public class YrRequest {

    private final String country;
    private final String city;
    private final String region;
    private final Interval interval;
    private String customUrl;

    public YrRequest(String country, String city, String region, int days) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.interval = generateInterval(days);
    }

    public YrRequest(String country, String city, String region, int days, String customUrl) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.interval = generateInterval(days);
        this.customUrl = customUrl;
    }

    private Interval generateInterval(int days) {
        return new Interval(new DateMidnight(), new DateMidnight().plusDays(days + 1));
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Interval getInterval() {
        return interval;
    }

    public String getRegion() {
        return region;
    }

    public String getCustomUrl() {
        return customUrl;
    }
    
}