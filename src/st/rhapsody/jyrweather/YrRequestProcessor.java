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

    private Document getNewDocument(URL url) throws IOException, JDOMException {
        InputStream stream = url.openStream();
        Document doc = SAXBUILDER.build(stream);
        DateTime nextUpdate = new DateTime(doc.getRootElement().getChild("meta").getChild("nextupdate").getText());

        cache.put(url, new CachedDocument(nextUpdate, doc));

        return doc;
    }
}
