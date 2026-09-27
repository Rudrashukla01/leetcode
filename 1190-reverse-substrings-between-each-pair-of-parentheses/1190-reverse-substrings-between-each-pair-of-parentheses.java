class Solution {
    public String reverseParentheses(String s) {
        StringBuilder result = new StringBuilder();
        java.util.Stack<Integer> stack = new java.util.Stack<>();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                stack.push(result.length());
            } 
            else if (ch == ')') {
                int start = stack.pop();

                StringBuilder temp = new StringBuilder(
                    result.substring(start)
                );

                temp.reverse();

                result.delete(start, result.length());
                result.append(temp);

            } 
            else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}