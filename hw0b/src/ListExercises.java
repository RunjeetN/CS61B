import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        if (L.size() == 0){
            return 0;
        }
        else{
             for(int i: L){
                 sum += i;
             }
             return sum; 
        }
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List even_List = new ArrayList<Integer>();
        for(int i : L){
            if(i % 2 == 0){
                even_List.add(i);
            }
        }
        if(even_List.size() == 0){
            return null;
        }
        return even_List;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List L3 = new ArrayList<Integer>();
        for(int i : L1){
            for(int k: L2){
                if(i == k && !L3.contains(i)){
                    L3.add(i);
                }
            }
        }
        return L3;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int sum=0;
        for(String i : words){
            if (i.indexOf(c) != -1){
                sum+=1;
            }
        }
        return sum;
    }
}
