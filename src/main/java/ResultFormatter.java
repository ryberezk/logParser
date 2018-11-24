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

    Map<Document, ArrayList> hashMap;

    public ResultFormatter(HashMap<Document, ArrayList> hashMap) {
        this.hashMap = hashMap;
    }

    //Формируем List с элементами только одного сервиса
    public List<Document> getCurrentArray(Map<Document, String> hashMap, SearchAttr attr) {
        List<Document> elementsOfService = new ArrayList<>();
        for (Map.Entry<Document, String> entry : hashMap.entrySet()) {
            String value = entry.getValue();
            if (attr.custInqRq) {
                if (value.equals("<custinqrq>") || value.equals("<custinqrs>")) {
                    elementsOfService.add(entry.getKey());
                }
            }
            if (attr.checkInStopListRq) {
                if (value.equals("<checkinstoplistrq>") || value.equals("<checkinstoplistrs>")) {
                    elementsOfService.add(entry.getKey());
                }
            }
        }
        return elementsOfService;
    }

    //Выбираем значения из списка елементов сервиса custinq
    public void getCustInqOfSearch(List<Document> elements, SearchAttr attr) {
        if (attr.number != null) {
            result(searchStringInArrays(elements, "idnum", attr.number));
        } else if (attr.seria != null) {

        } else if (attr.surname != null) {

        }
    }

    // Удаляем все значения, которые не содержат данных, указаных при поиске для List
    public List<Document>  searchStringInArrays(List<Document> elements, String rootTag, String tagname) {
        List<Document> elementsOfService = new ArrayList<>();
        for (Document document : elements) {
            if (document.getElementsByTagName(rootTag).getLength() > 0) {
                if (document.getElementsByTagName(rootTag).item(0).getTextContent().equals(tagname)) {
                    elementsOfService.add(document);
                }
            }
        }
        return elementsOfService;
    }

    //Вспомогательный метод для получения значений из xml
    public static String getTextValue(Node node) {
        StringBuffer textValue = new StringBuffer();
        int length = node.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            Node c = node.getChildNodes().item(i);
            if (c.getNodeType() == Node.TEXT_NODE) {
                textValue.append(c.getNodeValue());
            }
        }
        return textValue.toString().trim();
    }

    public void result(List<Document> xml) {

        for (Document document : xml) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-S");
                Calendar cal = Calendar.getInstance();
                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                Source src = new DOMSource(document);
                Result dest = new StreamResult(new File(dateFormat.format(cal.getTime()) + " xmlFileName.xml"));

                aTransformer.transform(src, dest);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void resultForMap(Map<Document, Document> xml) throws TransformerException {

        for (Map.Entry<Document, Document> entry : xml.entrySet()) {
            entry.getKey();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-S");
            Calendar cal = Calendar.getInstance();
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            Source src = new DOMSource(entry.getKey());
            Result dest = new StreamResult(new File( entry.getKey().getDocumentElement().getTagName()+ " " + entry.getKey().getElementsByTagName("rquid").item(0).getTextContent()+ " " + dateFormat.format(cal.getTime()) + ".xml"));
            aTransformer.transform(src, dest);

            entry.getValue();
            src = new DOMSource(entry.getValue());
            dest = new StreamResult(new File(entry.getKey().getDocumentElement().getTagName()+ " " + entry.getKey().getElementsByTagName("rquid").item(0).getTextContent()+ " " + dateFormat.format(cal.getTime()) + ".xml"));
            aTransformer.transform(src, dest);
        }
    }
}
