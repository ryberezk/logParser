package transferAttributes;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResultFormatter {

    public Map<Document, ArrayList> hashMap;

    public ResultFormatter(HashMap<Document, ArrayList> hashMap) {
        this.hashMap = hashMap;
    }

    public void result(List<Document> list) throws TransformerException {

        for (Iterator<Document> iter = list.iterator(); iter.hasNext(); ) {
            Document element = iter.next();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-S");
            Calendar cal = Calendar.getInstance();
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            Source src = new DOMSource(element);
            Result dest = new StreamResult(new File(element.getDocumentElement().getTagName() + " " + element.getElementsByTagName("rquid").item(0).getTextContent() + " " + dateFormat.format(cal.getTime()) + ".xml"));
            aTransformer.transform(src, dest);
        }
    }
}
