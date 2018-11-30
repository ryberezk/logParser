import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyFileReader {

    static String line = null;
    static String currentLine = null;
    static int currLine = 0;
    static int wordPosStart = 0;
    static int wordPosEnd = 0;
    String xmlConstant = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";


    HashMap<Document, ArrayList> results = new HashMap<>();


    public HashMap<Document, ArrayList> getServiceMessages(String path, SearchAttr attr) {

        attr.getListOfServicesForSearch();
        ArrayList<String> words = getWordsForSearch(attr);
        Map<String, String> hashMap = attr.hashMap;
        ArrayList xmlAttributes = new ArrayList();


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
                        if (currentLine.contains(hashMap.get(word)) && (getTime(currentLine) != null)) {
                            wordPosEnd = currentLine.indexOf(hashMap.get(word));
                            xmlAttributes.add(word);
                            xmlAttributes.add(getTime(currentLine));
                            results.put(getXmlDocument(currentLine.substring(wordPosStart, wordPosEnd) + hashMap.get(word)), new ArrayList(xmlAttributes));
                            xmlAttributes.clear();
                        }

                    }
                }
            }
            bR.close();
            fr.close();
            words.clear();
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e.toString());
        } catch (SAXException | ParserConfigurationException | ParseException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ArrayList<String> getWordsForSearch(SearchAttr attr) {
        ArrayList<String> words = new ArrayList<String>();
        attr.getListOfServicesForSearch();
        for (Map.Entry<String, String> entry : attr.hashMap.entrySet()) {
            words.add(entry.getKey());
        }
        return words;
    }


    public Date getTime(String line) throws ParseException {
        if (line.contains("msk]")) {
            String dateAndTime = line.substring(line.indexOf("[") + 1, (line.indexOf("]") - 3));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat parser = new SimpleDateFormat("dd/M/yy HH:mm:ss:S");
            Date date = parser.parse(dateAndTime);
            return date;
        }
        return null;
    }

    public Document getXmlDocument(String line) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(line)));
        document.getDocumentElement().normalize();

        return document;

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

