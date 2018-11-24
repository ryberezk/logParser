import org.w3c.dom.Document;

import java.util.*;

public class ServiceHandler {

    //Формируем List с элементами только одного сервиса
    public Map<Document, Document> getCustInq(Map<Document, ArrayList> hashMap, SearchAttr attr) {
        List<Document> elementsOfService = new ArrayList<>();
        for (Map.Entry<Document, ArrayList> entry : hashMap.entrySet()) {
            String value = (String) entry.getValue().get(0);
            Date date = (Date) entry.getValue().get(1);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -15);
            if (attr.custInqRq) {
                if (value.equals("<custinqrq>") || value.equals("<custinqrs>")) {
                    if (!entry.getKey().getElementsByTagName("rquid").item(0).getTextContent().startsWith("bbbb")) {
                        if (entry.getKey().getElementsByTagName("idnum").getLength() > 0) {
                            if (entry.getKey().getElementsByTagName("idnum").item(0).getTextContent().equals(attr.number)) {
                                if (date.after(calendar.getTime())) {
                                    if (entry.getKey().getElementsByTagName("custinqrq").getLength() > 0 || entry.getKey().getElementsByTagName("searchtype").getLength() > 0) {
                                        elementsOfService.add(entry.getKey());
                                    } else if
                                    (entry.getKey().getElementsByTagName("custinqrs").getLength() > 0 || entry.getKey().getElementsByTagName("rbtbbrchid").getLength() < 0) {
                                        elementsOfService.add(entry.getKey());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return getOneRequests(elementsOfService);
    }

    //Оставляем только запросы с ответами
    public Map<Document, Document> getOneRequests(List<Document> documents) {
        String rquid;
        Map<Document, Document> requestResponse = new HashMap<>();
        while (documents.iterator().hasNext()) {
            Document document = documents.iterator().next();
            rquid = document.getElementsByTagName("rquid").item(0).getTextContent();
            while (documents.iterator().hasNext()) {
                Document documentSecond = documents.iterator().next();
                if (rquid.equals(document.getElementsByTagName("rquid").item(0).getTextContent())) {
                    if (document.getFirstChild().getTextContent().endsWith("rq")) {
                        requestResponse.put(document, documentSecond);
                    } else {
                        requestResponse.put(documentSecond, document);
                    }
                    documents.remove(document);
                    documents.remove(documentSecond);
                    break;
                }
            }
        }
        return requestResponse;
    }
}

