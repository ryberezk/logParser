import java.util.HashMap;
import java.util.Map;

public class SearchAttr {
    boolean custInqRq;
    boolean checkInStopListRq;
    String searchWord;

    Map<String, String> hashMap = new HashMap<>();

    public Map<String, String> getHashMap() {
        return hashMap;
    }

    public void getListOfServicesForSearch () {

        if (this.custInqRq){
            hashMap.put("<custinqrq>", "</custinqrq>");
        }
        if (this.checkInStopListRq){
            hashMap.put("<checkinstoplistrq>", "</checkinstoplistrq>");
        }
    }

}
