package service;

import java.util.Arrays;

public class test {
    public int[] plusOne(int[] digits) {
        // البدء من آخر رقم (الأقل أهمية)
        for (int i = digits.length - 1; i >= 0; i--) {
            // إذا كان الرقم أقل من 9، نضيف 1 ونرجع النتيجة
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            // إذا كان الرقم = 9، يصبح 0 ونكمل للرقم التالي (carry)
            digits[i] = 0;
        }
        
        // إذا وصلنا هنا، معناه كل الأرقام كانت 9
        // مثل: [9,9,9] يصبح [1,0,0,0]
        int[] result = new int[digits.length + 1];
        result[0] = 1; // باقي العناصر تكون 0 بشكل افتراضي
        return result;
    }

    public static void main(String[] args) {
        int[] digits={9,9,9};
        System.out.println(Arrays.toString(new test().plusOne(digits)));
    }
}
