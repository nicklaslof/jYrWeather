package st.rhapsody.jyrweather;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;

public class YrRequestProcessor {

    private final SAXBuilder SAXBUILDER = new SAXBuilder(false);
    private final static YrRequestProcessor instance = new YrRequestProcessor();
    private final Map<URL, CachedDocument> cache = new HashMap<URL, CachedDocument>();

    public static YrRequestProcessor getInstance() {
        return instance;
    }

    private YrRequestProcessor() {
    }

    public YrResult getResult(YrRequest request) throws MalformedURLException, IOException, JDOMException {

        URL yrUrl = generateYrUrl(request);
        Document doc = readDocumentFromCache(yrUrl);
        Element weatherdata = doc.getRootElement();
        YrResult yrResult = createYrResult(weatherdata);

        List<Element> children = weatherdata.getChild("forecast").getChild("tabular").getChildren("time");

        for (Element element : children) {
            DateTime dt = new DateTime(element.getAttributeValue("from"));
            if (insideRequestInterval(request, dt)) {
                populateListWithWeatherData(element, request, yrResult);
            }
        }
        return yrResult;
    }

    private boolean insideRequestInterval(YrRequest request, DateTime dt) {
        return request.getInterval().contains(dt);
    }

    private YrResult createYrResult(Element weatherdata) {
        DateTime sunrise = new DateTime(weatherdata.getChild("sun").getAttributeValue("rise"));
        DateTime sunset = new DateTime(weatherdata.getChild("sun").getAttributeValue("set"));

        String creditLinkText = weatherdata.getChild("credit").getChild("link").getAttributeValue("text");
        String creditLinkUrl = weatherdata.getChild("credit").getChild("link").getAttributeValue("url");
        DateTime lastUpdate = new DateTime(weatherdata.getChild("meta").getChild("lastupdate").getText());
        DateTime nextUpdate = new DateTime(weatherdata.getChild("meta").getChild("nextupdate").getText());
        return new YrResult(sunrise, sunset, creditLinkText, creditLinkUrl, lastUpdate, nextUpdate);
    }

    private URL generateYrUrl(YrRequest query) throws MalformedURLException {
        if (query.getCustomUrl() != null) {
            return new URL(query.getCustomUrl());
        }
        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder.append("http://www.yr.no/place/").append(query.getCountry()).append("/").append(query.getCity()).append("/").append(query.getRegion()).append("/forecast.xml");
        return new URL(urlStringBuilder.toString());
    }

    private Document readDocumentFromCache(URL url) throws IOException, JDOMException {
        Document doc;
        if (cache.containsKey(url)) {
            if (cache.get(url).getDate().isBeforeNow()) {
                doc = getNewDocument(url);
            } else {
                doc = cache.get(url).getDocument();
            }
        } else {
            doc = getNewDocument(url);
        }
        return doc;
    }

    private void populateListWithWeatherData(Element element, YrRequest yrRequest, YrResult yrResult) throws NumberFormatException {
        DateTime from = new DateTime(element.getAttributeValue("from"));
        DateTime to = new DateTime(element.getAttributeValue("from"));
        String forecast = element.getChild("symbol").getAttributeValue("name");
        int symbol = Integer.valueOf(element.getChild("symbol").getAttributeValue("number"));
        float windDirectionDeg = Float.valueOf(element.getChild("windDirection").getAttributeValue("deg"));
        String windDirectionCode = element.getChild("windDirection").getAttributeValue("code");
        String windDirectonName = element.getChild("windDirection").getAttributeValue("name");
        float windSpeed = Float.valueOf(element.getChild("windSpeed").getAttributeValue("mps"));
        String windName = element.getChild("windSpeed").getAttributeValue("name");
        String temperatureUnit = element.getChild("temperature").getAttributeValue("unit");
        int temperature = Integer.valueOf(element.getChild("temperature").getAttributeValue("value"));
        String pressureUnit = element.getChild("pressure").getAttributeValue("unit");
        float pressureValue = Float.valueOf(element.getChild("pressure").getAttributeValue("value"));
        float precipitation = Float.valueOf(element.getChild("precipitation").getAttributeValue("value"));

        YrForecast yrForecast = new YrForecast(yrRequest, forecast, symbol, windDirectionDeg, windDirectionCode, windDirectonName, windSpeed, windName, pressureUnit, pressureValue, precipitation, temperature, temperatureUnit, from, to);
        yrResult.addYrForecast(yrForecast);
    }

    private Document getNewDocument(URL url) throws IOException, JDOMException {
        InputStream stream = url.openStream();
        Document doc = SAXBUILDER.build(stream);
        DateTime nextUpdate = new DateTime(doc.getRootElement().getChild("meta").getChild("nextupdate").getText());

        cache.put(url, new CachedDocument(nextUpdate, doc));

        return doc;
    }
}
