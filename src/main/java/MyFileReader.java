import org.w3c.dom.Document;
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

    private String path;
    private RandomAccessFile file;

    static String line;
    static ArrayList<String> words = new ArrayList<String>();
    static String lineLC = null;
    static int currLine = 1;
    static int wordPosStart = 0;
    static int wordPosEnd = 0;


    List<Integer> currLines = new ArrayList<Integer>();
    Map<String, String> hashMap = new HashMap<>();


    public List searchRequests(String path, SearchAttr attr) {
        String word;
        hashMap.put("<custinqrq>", "</custinqrq>");
        hashMap.put("<custinqrs>", "</custinqrs>");
        if (attr.custinqrq) {
            words.add("<custinqrq>");
        }
        if (attr.custinqrs) {
            words.add("<custinqrs>");
        }

        FileReader fr = getFileReader(path);
        BufferedReader bR = getBufferedReader(fr);
        try {
            while ((line = bR.readLine()) != null) {
                lineLC = line.toLowerCase();
                currLine++;

                for (int w = 0; w < words.size(); w++) {
                    word = words.get(w).toLowerCase();

                    if (lineLC.contains(word)) {
                        wordPosStart = lineLC.lastIndexOf(word);
                        currLines.add(currLine - 1);
                        while ((line = bR.readLine()) != null) {
                            if (lineLC.contains(hashMap.get(word))) {
                                wordPosEnd = lineLC.indexOf(hashMap.get(word));
                                if (lineLC.contains(attr.searchWord)) {
                                    System.out.println(getTime(lineLC) + getXml(lineLC.substring(wordPosStart, wordPosEnd) + hashMap.get(word)));
                                }
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
        } catch (ParseException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return currLines;
    }

    public String getTime(String line) throws ParseException {
        if (line.contains("[")) {
            String dateAndTime = line.substring(line.indexOf("[")+1, (line.indexOf("]") -3));
            SimpleDateFormat parser = new SimpleDateFormat("d/m/yy HH:mm:ss:S");
            Date date = parser.parse(dateAndTime);
            return date.toString();
        } return "Дата запроса не найдена";
    }

    public Document getXml (String line) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(line)));
    }

    private BufferedReader getBufferedReader(FileReader fileReader) {
        BufferedReader br = null;
        br = new BufferedReader(fileReader);
        return br;
    }

    private FileReader getFileReader(String path) {
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

