package lab1;

public class problem5_table_printer {
    public static void main(String[] args) {
        System.out.println("{");
        long min;
        for (min = 0; min < 999999666667L; min++) {
            if (isMagicNumber(min))
                System.out.println(min+",");
//                break;
            if(199986662<= min && min<=600000008)
                min = 600000008;
        }
        System.out.println("}");
    }
    private static boolean isMagicNumber(long i) {
//        StringBuilder sb = new StringBuilder(i+"").reverse();
        final char[] chars = (i+"").toCharArray();
        for (int j = 0; j < chars.length; j++) {
            switch (chars[j]) {
                case '0':
                case '1':
                case '8':
                    break;
                case '6':
                    chars[j] = '9';
                    break;
                case '9':
                    chars[j] = '6';
                    break;
                default:
                    return false;
            }
        }
        return new StringBuilder(String.valueOf(chars)).reverse().toString().equals(i+"");
    }
}
