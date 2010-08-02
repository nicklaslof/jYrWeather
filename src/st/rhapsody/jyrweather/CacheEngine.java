/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package st.rhapsody.jyrweather;

import java.io.IOException;
import java.net.URL;
import org.jdom.Document;
import org.jdom.JDOMException;

/**
 *
 * @author nicklas
 */
public interface CacheEngine {
    Document readDocumentFromCache(URL url) throws IOException, JDOMException;

}
