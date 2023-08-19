import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> map = new TreeMap<>();
        int[] nums = {1, 2, 3, 4, 5, 6,7, 8, 9, 10, 11, 12, 13, 14, 15,16,17,18,19,20,21,22,23,24,25,26};
        Character[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n', 'o','p','q','r','s','t','u','v', 'w','x','y','z'};
        for(int i = 0; i < letters.length; i++){
            map.put(letters[i], nums[i]);
        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> map = new TreeMap<>();
        for(int i:nums){
            map.put(i, i*i);
        }
        return map;

    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> map = new TreeMap<>();
        for(String word: words){
            if (!map.containsKey(word)){
                map.put(word, 1);
            }
            else{
                map.put(word, map.get(word) + 1);
            }
        }
        return map;
    }
}
