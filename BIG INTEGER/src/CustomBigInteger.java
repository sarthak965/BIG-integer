import java.util.ArrayList;
import java.util.List;

public class CustomBigInteger {
    private List<Integer> digits;

    public CustomBigInteger(String number) {
        digits = new ArrayList<>();
        for (int i = number.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(number.charAt(i))) {
                throw new IllegalArgumentException("Invalid character in number string.");
            }
            digits.add(number.charAt(i) - '0');
        }
    }

    private CustomBigInteger(List<Integer> digits) {
        this.digits = digits;
    }

    public CustomBigInteger add(CustomBigInteger other) {
        List<Integer> result = new ArrayList<>();
        int carry = 0;
        int maxLength = Math.max(this.digits.size(), other.digits.size());

        for (int i = 0; i < maxLength; i++) {
            int digit1 = (i < this.digits.size()) ? this.digits.get(i) : 0;
            int digit2 = (i < other.digits.size()) ? other.digits.get(i) : 0;
            int sum = digit1 + digit2 + carry;
            result.add(sum % 10);
            carry = sum / 10;
        }

        if (carry != 0) {
            result.add(carry);
        }

        return new CustomBigInteger(result);
    }

    public CustomBigInteger multiply(CustomBigInteger other) {
        int[] result = new int[this.digits.size() + other.digits.size()];

        for (int i = 0; i < this.digits.size(); i++) {
            int carry = 0;
            for (int j = 0; j < other.digits.size(); j++) {
                int product = this.digits.get(i) * other.digits.get(j) + result[i + j] + carry;
                result[i + j] = product % 10;
                carry = product / 10;
            }
            if (carry > 0) {
                result[i + other.digits.size()] += carry;
            }
        }

        List<Integer> resultList = new ArrayList<>();
        boolean leadingZero = true;
        for (int i = result.length - 1; i >= 0; i--) {
            if (leadingZero && result[i] == 0) {
                continue;
            }
            leadingZero = false;
            resultList.add(result[i]);
        }

        if (resultList.isEmpty()) {
            resultList.add(0);
        }

        return new CustomBigInteger(resultList);
    }

    public CustomBigInteger factorial() {
        CustomBigInteger result = new CustomBigInteger("1");
        CustomBigInteger one = new CustomBigInteger("1");

        for (CustomBigInteger i = new CustomBigInteger("2"); i.compareTo(this) <= 0; i = i.add(one)) {
            result = result.multiply(i);
        }

        return result;
    }

    private int compareTo(CustomBigInteger other) {
        if (this.digits.size() != other.digits.size()) {
            return this.digits.size() - other.digits.size();
        }
        for (int i = this.digits.size() - 1; i >= 0; i--) {
            if (!this.digits.get(i).equals(other.digits.get(i))) {
                return this.digits.get(i) - other.digits.get(i);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(digits.size());
        for (int i = digits.size() - 1; i >= 0; i--) {
            sb.append(digits.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomBigInteger num1 = new CustomBigInteger("12345678901234567890");
        CustomBigInteger num2 = new CustomBigInteger("98765432109876543210");

        CustomBigInteger sum = num1.add(num2);
        System.out.println("Sum: " + sum);

        CustomBigInteger product = num1.multiply(num2);
        System.out.println("Product: " + product);

        CustomBigInteger factorial = new CustomBigInteger("20").factorial();
        System.out.println("Factorial: " + factorial);
    }
}
