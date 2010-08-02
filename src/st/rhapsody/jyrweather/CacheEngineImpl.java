/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st.rhapsody.jyrweather;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;

/**
 *
 * @author nicklas
 */
public class CacheEngineImpl implements CacheEngine {

    private final Map<URL, CachedDocument> cache = new HashMap<URL, CachedDocument>();
    private final SAXBuilder SAXBUILDER = new SAXBuilder(false);

    public Document readDocumentFromCache(URL url) throws IOException, JDOMException {
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

    private Document getNewDocument(URL url) throws IOException, JDOMException {
        InputStream stream = url.openStream();
        Document doc = SAXBUILDER.build(stream);
        DateTime nextUpdate = new DateTime(doc.getRootElement().getChild("meta").getChild("nextupdate").getText());
        cache.put(url, new CachedDocument(nextUpdate, doc));
        return doc;
    }
}