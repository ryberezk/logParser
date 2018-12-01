package servicesHandlers;

import org.w3c.dom.Document;
import transferAttributes.RequestAttributes;
import transferAttributes.ResponseData;

import java.util.*;

public class ServiceHandler {

    //Формируем List с элементами только одного сервиса
    public List <Document> getCustInq(Map<Document, ArrayList> hashMap, RequestAttributes requestAttributes, ResponseData responseData) {
        List<Document> elementsOfService = new ArrayList<>();
        for (Map.Entry<Document, ArrayList> entry : hashMap.entrySet()) {
            String value = (String) entry.getValue().get(0);
            Date date = (Date) entry.getValue().get(1);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -15);
            if (requestAttributes.custInqRq) {
                if (value.equals("<custinqrq>") || value.equals("<custinqrs>")) {
                    if (!entry.getKey().getElementsByTagName("rquid").item(0).getTextContent().startsWith("bbbb")) {
                        if (entry.getKey().getElementsByTagName("idnum").getLength() > 0) {
                            if (entry.getKey().getElementsByTagName("idnum").item(0).getTextContent().equals(requestAttributes.number)) {
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
        responseData.getCustInq = elementsOfService;
        return elementsOfService;
    }
}

