package st.rhapsody.jyrweather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.joda.time.DateTime;

public class YrRequestProcessor {


    private final static YrRequestProcessor instance = new YrRequestProcessor();
    private final static CacheEngine cacheEngine = new CacheEngineImpl();


    public static YrRequestProcessor getInstance() {
        return instance;
    }

    private YrRequestProcessor() {
    }

    public YrResult getResult(YrRequest request) throws MalformedURLException, IOException, JDOMException {
        return getResult(request,cacheEngine);
    }

    public YrResult getResult(YrRequest request, CacheEngine cacheEngine) throws MalformedURLException, IOException, JDOMException {

        URL yrUrl = generateYrUrl(request);
        Document doc = cacheEngine.readDocumentFromCache(yrUrl);
        Element weatherdata = doc.getRootElement();
        YrResult yrResult = new YrResult(weatherdata);

        List<Element> children = weatherdata.getChild("forecast").getChild("tabular").getChildren("time");

        for (Element element : children) {
            DateTime dt = new DateTime(element.getAttributeValue("from"));
            if (insideRequestInterval(request, dt)) {
                populateResultWithForecast(element, yrResult);
            }
        }
        return yrResult;
    }

    private boolean insideRequestInterval(YrRequest request, DateTime dt) {
        return request.getInterval().contains(dt);
    }

    private URL generateYrUrl(YrRequest query) throws MalformedURLException {
        if (query.getCustomUrl() != null) {
            return new URL(query.getCustomUrl());
        }
        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder.append("http://www.yr.no/place/").append(query.getCountry()).append("/").append(query.getCity()).append("/").append(query.getRegion()).append("/forecast.xml");
        return new URL(urlStringBuilder.toString());
    }
    private void populateResultWithForecast(Element element, YrResult yrResult) throws NumberFormatException {
        yrResult.addYrForecast(new YrForecast(element));
    }

    static String getXMLValue(Element element, String attribute, String... childs) {
        Element elementChild = element;
        if (childs != null) {
            for (String string : childs) {
                elementChild = elementChild.getChild(string);
            }
        }
        return elementChild.getAttributeValue(attribute);
    }
}
