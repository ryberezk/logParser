import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                System.out.println(lineLC.substring(wordPosStart, wordPosEnd) + hashMap.get(word));

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
        }
        return currLines;
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

    // читаем файл с определенного символа
    public String readFrom(int numberSymbol, String path) throws IOException {

        file = new RandomAccessFile(path, "r");
        String res = "";
        file.seek(numberSymbol);
        int b = file.read();

        while (b != -1) {
            res = res + (char) b;

            b = file.read();
        }
        file.close();

        return res;
    }

    // запись в файл
    public void write(String st) throws IOException {
        // открываем файл для записи
        // для этого указываем модификатор rw (read & write)
        // что позволит открыть файл и записать его
        file = new RandomAccessFile(path, "rw");

        // записываем строку переведенную в биты
        file.write(st.getBytes());

        // закрываем файл, после чего данные записываемые данные попадут в файл
        file.close();
    }
}

