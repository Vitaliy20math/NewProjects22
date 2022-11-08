import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String Calc(String input){

        int count = 0;

        String result = "";

        char[] str = input.toCharArray();

        for(int i =0; i < str.length; ++i){
            //System.out.println(str[i]);
            if(str[i] == '+' || str[i] == '-' || str[i] == '/' || str[i] == '*'){
                count++;
            }
        }
        if(count == 0){
            try{
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("В строке отсутствует математический оператор");
            }
        }else if(count > 1){
            try{
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Формат математической операции не удовлетворяет заданию");
            }
        }else if(count == 1){
            //определяем позицию знака в строке
            int position_operator = 0;
            for(int i =0; i < str.length; ++i){
                if(str[i] == '+' || str[i] == '-' || str[i] == '/' || str[i] == '*'){
                    position_operator = i;
                }
            }


            //смотрим на число слева от оператора
            char [] left_operand = new char[position_operator];
            for(int i =0; i < position_operator;++i){
                left_operand[i] = str[i];
            }

            //смотрим на число справа от оператора
            char [] right_operand = new char[str.length - (position_operator + 1)];
            int k = 0;
            for(int i = position_operator+1; i < str.length;++i){
                right_operand[k] = str[i];
                ++k;
            }

            if(left_operand.length > 0 && right_operand.length > 0){
                String leftOperand = "";
                for(int i = 0; i < left_operand.length;++i){
                    leftOperand+=left_operand[i];
                }
                //System.out.println("left-operand = " + leftOperand);
                String rightOperand = "";
                for(int i = 0; i < right_operand.length;++i){
                    rightOperand+=right_operand[i];
                }
                //System.out.println("right-operand = " + rightOperand);
                String operator = "";
                operator += str[position_operator];
                //System.out.println("operator = " + operator);

                /*Pattern pattern = Pattern.compile("[0-9]");
                Matcher first_num_arab = pattern.matcher(leftOperand);
*/
                if(leftOperand.matches("^([1-9]|10)$") && rightOperand.matches("^([1-9]|10)$")) {
                    int first_num = Integer.parseInt(leftOperand);
                    int second_num = Integer.parseInt(rightOperand);
                    int sum, dif= 0;
                    int prod, div = 1;
                    if(str[position_operator] == '+'){
                        sum = first_num + second_num;
                        result = Integer.toString(sum);
                    }
                    if(str[position_operator] == '-'){
                         dif = first_num - second_num;
                        result = Integer.toString(dif);
                    }
                    if(str[position_operator] == '*'){
                        prod = first_num * second_num;
                        result = Integer.toString(prod);
                    }
                    if(str[position_operator] == '/'){
                        div = first_num / second_num;
                        result = Integer.toString(div);
                    }

                }else if(leftOperand.matches("^([I]*|[IV]+|V|X|IX)$") && rightOperand.matches("^([I]*|[IV]+|V|X|IX)$")){

                    HashMap<String, Integer> arabic_rim = new HashMap<>();
                    HashMap<Integer, String> rimNum = new HashMap<>();
                    HashMap<Integer, String> rimNumOneTen = new HashMap<>();

                    rimNum.put(0, "");
                    rimNum.put(10, "X");
                    rimNum.put(20, "XX");
                    rimNum.put(30, "XXX");
                    rimNum.put(40, "XL");
                    rimNum.put(50, "L");
                    rimNum.put(60, "LX");
                    rimNum.put(70, "LXX");
                    rimNum.put(80, "LXXX");
                    rimNum.put(90, "XC");
                    rimNum.put(100, "C");

                    rimNumOneTen.put(0, "");
                    rimNumOneTen.put(1, "I");
                    rimNumOneTen.put(2, "II");
                    rimNumOneTen.put(3, "III");
                    rimNumOneTen.put(4, "IV");
                    rimNumOneTen.put(5, "V");
                    rimNumOneTen.put(6, "VI");
                    rimNumOneTen.put(7, "VII");
                    rimNumOneTen.put(8, "VIII");
                    rimNumOneTen.put(9, "IX");
                    rimNumOneTen.put(10, "X");

                    arabic_rim.put("I", 1);
                    arabic_rim.put("II", 2);
                    arabic_rim.put("III", 3);
                    arabic_rim.put("IV", 4);
                    arabic_rim.put("V", 5);
                    arabic_rim.put("VI", 6);
                    arabic_rim.put("VII", 7);
                    arabic_rim.put("VIII", 8);
                    arabic_rim.put("IX", 9);
                    arabic_rim.put("X", 10);


                    int first_num = arabic_rim.get(leftOperand);
                    int second_num = arabic_rim.get(rightOperand);
                    int sum, dif= 0;
                    int prod, div = 1;

                    if(str[position_operator] == '+'){
                        sum = first_num + second_num;
                        int units = 0;

                        if(sum == 20){
                            result+=rimNum.get(sum);
                        }else if(sum < 20){
                            int decimal = sum/10;
                            if(decimal > 0){
                                units = sum - decimal * 10;
                                result+= rimNum.get(decimal*10);
                                result+=rimNumOneTen.get(units);
                            }else{
                                result+=rimNumOneTen.get(units);
                            }

                        }


                    }
                    if(str[position_operator] == '-'){
                        dif = first_num - second_num;

                        if(second_num > first_num){
                            try{
                                throw new Exception();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("В Римской системе нет отрицательных чисел");
                            }
                        }else if(first_num == second_num){
                            result+=0;
                        }else{
                            result+=rimNumOneTen.get(first_num - second_num);
                        }


                    }
                    if(str[position_operator] == '*'){
                        prod = first_num * second_num;

                        int hundreds = prod/100;
                        int decimal = (prod - hundreds*100)/10;
                        int units = prod - (hundreds * 100 + decimal * 10);
                        if(hundreds==1){

                            result += rimNum.get(hundreds*100);
                            result += rimNum.get(decimal*10);
                            result += rimNumOneTen.get(units);

                        }else if(hundreds == 0){
                            if(decimal >= 1){
                                result += rimNum.get(decimal*10);
                                result+= rimNumOneTen.get(units);
                            }else if(decimal == 0){
                                result += rimNumOneTen.get(units);
                            }
                        }

                    }
                    if(str[position_operator] == '/'){
                        div = first_num / second_num;
                        int hundreds = div/100;
                        int decimal = (div - hundreds)/10;
                        int units = div - (hundreds * 100 + decimal * 10);
                        result+=rimNumOneTen.get(units);
                    }


                }else{
                    try{
                        throw new Exception();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Неверн(йы/ые) операнд/ы");
                    }
                }
            }else{
                try{
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Не хватает операнда");
                }
            }





        }



        return result;
    }



    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            String input = scanner.nextLine().trim();
            System.out.println(Calc(input));

            //System.out.println(line);
        }
    }
}
