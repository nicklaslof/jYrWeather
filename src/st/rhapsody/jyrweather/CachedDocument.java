package st.rhapsody.jyrweather;

import org.jdom.Document;
import org.joda.time.DateTime;

class CachedDocument {
    private final DateTime date;
    private final Document doc;

    CachedDocument(DateTime date, Document doc) {
        this.date = date;
        this.doc = doc;

    }

    public DateTime getDate() {
        return date;
    }

    public Document getDocument() {
        return doc;
    }



}
