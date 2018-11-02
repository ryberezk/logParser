import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class MyFileReader {

    static String line;
    static String currentLine = null;
    static int currLine = 0;
    static int wordPosStart = 0;
    static int wordPosEnd = 0;


    List<Element> results = new ArrayList<Element>();
    Map<String, String> hashMap = new HashMap<>();
    ArrayList<String> words;

    public List<Element> searchRequests(String path, SearchAttr attr) {

        attr.getListOfServicesForSearch();
        words = getWordsForSearch(attr);
        Map<String, String> hashMap = attr.hashMap;

        FileReader fr = getFileReader(path);
        BufferedReader bR = getBufferedReader(fr);
        try {
            while ((line = bR.readLine()) != null) {
                currentLine = line.toLowerCase();
                currLine++;

                for (int w = 0; w < words.size(); w++) {
                    String word = words.get(w).toLowerCase();

                    if (currentLine.contains(word)) {
                        wordPosStart = currentLine.lastIndexOf(word);
                        while ((line = bR.readLine()) != null) {
                            if (currentLine.contains(hashMap.get(word))) {
                                wordPosEnd = currentLine.indexOf(hashMap.get(word));
                                    results.add(getXml(currentLine.substring(wordPosStart, wordPosEnd) + hashMap.get(word)));
                                    System.out.println(getStringFromXmlByTagName("rquid",getXml(currentLine.substring(wordPosStart, wordPosEnd) + hashMap.get(word))));
                                break;
                            }
                        }
                    }
                }
            }
            bR.close();
            fr.close();
            words.clear();
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e.toString());
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ArrayList<String> getWordsForSearch (SearchAttr attr) {
        ArrayList<String> words = new ArrayList<String>();
        attr.getListOfServicesForSearch();

        for (Map.Entry<String, String> entry : attr.hashMap.entrySet()) {
            words.add(entry.getKey());
        }
        return words;
    }

    // Пока комменчу. Потом будуиспользовать для сервисов без даты (типо открытия сессии)
//    public Date getTime(String line) throws ParseException {
//        if (line.contains("[")) {
//            String dateAndTime = line.substring(line.indexOf("[")+1, (line.indexOf("]") -3));
//            SimpleDateFormat parser = new SimpleDateFormat("d/m/yy HH:mm:ss:S");
//            Date date = parser.parse(dateAndTime);
//            return date;
//        } return null;
//    }

    public Element getXml (String line) throws IOException, SAXException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try
            {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse( new InputSource( new StringReader( line ) ) );
                return doc.getDocumentElement();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public String getStringFromXmlByTagName (String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return "Элемент не найден";
    }

    public BufferedReader getBufferedReader(FileReader fileReader) {
        BufferedReader br = null;
        br = new BufferedReader(fileReader);
        return br;
    }

    public FileReader getFileReader(String path) {
        FileReader fileReader = null;
        try {
            File f = new File(path);
            fileReader = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileReader;
    }
}

