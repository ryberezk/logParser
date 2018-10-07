import java.io.*;
import java.nio.charset.StandardCharsets;

public class NewFileReader {

    private String path;
    private RandomAccessFile file;

    public NewFileReader(String path) {
        this.path = path;
    }

    // метод демонстрирует переход на указанный символ
    public long goTo(int num) throws IOException {
        file = new RandomAccessFile(path, "r");
        file.seek(num);

        long pointer = file.getFilePointer();
        file.close();
        return pointer;
    }

    public String read() throws IOException {
        file = new RandomAccessFile(path, "r");
        String res = "";
        int b = file.read();
        // побитово читаем символы и плюсуем их в строку
        while (b != -1) {
            res = res + (char) b;
            b = file.read();
        }
        file.close();
        System.out.println(res);
        return res;
    }

    // читаем файл с определенного символа
    public String readFrom(int numberSymbol) throws IOException {
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