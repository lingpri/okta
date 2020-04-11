import java.util.Arrays;
import java.util.List;

public class AhTheCollection {

    //List View Of Array
    //Collections Corner Cases - by Stuart Marks

    public static void main(String[] args){

        String[] array = {"a","b","c","d","e","f","g"};
        List<String> list = Arrays.asList(array);
        array[0] = "XXX";
        System.out.println(list);
        list.set(1,"yyyy");
        System.out.println(list);

    }


}
