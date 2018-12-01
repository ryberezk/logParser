package transferAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestAttributes {
    public boolean custInqRq;
    public boolean checkInStopListRq;

    public String seria;
    public String number;
    public String surname;

    public HashMap<String, String> hashMap = new HashMap<>();

    public void getListOfServicesForSearch() {

        if (this.custInqRq) {
            hashMap.put("<custinqrq>", "</custinqrq>");
            hashMap.put("<custinqrs>", "</custinqrs>");
        }
        if (this.checkInStopListRq) {
            hashMap.put("<checkinstoplistrq>", "</checkinstoplistrq>");
            hashMap.put("<checkinstoplistrs>", "</checkinstoplistrs>");
        }
    }

}
