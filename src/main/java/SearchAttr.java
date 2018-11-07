import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAttr {
    boolean custInqRq;
    boolean checkInStopListRq;

    String seria;
    String number;
    String surname;

    HashMap<String, String> hashMap = new HashMap<>();

//    public Map<String, String> getHashMap() {
//        return hashMap;
//    }

    public void getListOfServicesForSearch () {

        if (this.custInqRq){
            hashMap.put("<custinqrq>", "</custinqrq>");
            hashMap.put("<custinqrs>", "</custinqrs>");
        }
        if (this.checkInStopListRq){
            hashMap.put("<checkinstoplistrq>", "</checkinstoplistrq>");
            hashMap.put("<checkinstoplistrs>", "</checkinstoplistrs>");
        }
    }

    public void getBooleanValue () {
        List<Boolean> values = new ArrayList<>();

    }

}
