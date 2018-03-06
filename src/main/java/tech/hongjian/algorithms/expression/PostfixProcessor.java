package tech.hongjian.algorithms.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class PostfixProcessor {
	private static final Set<String> OPERATORS = new HashSet<>();
	private static final Map<String, Integer> OPERATOR_WEIGHT = new HashMap<>();

	static {
		OPERATORS.add("+");
		OPERATORS.add("-");
		OPERATORS.add("*");
		OPERATORS.add("/");
		OPERATORS.add("(");
		OPERATORS.add(")");

		OPERATOR_WEIGHT.put("+", 0);
		OPERATOR_WEIGHT.put("-", 0);
		OPERATOR_WEIGHT.put("*", 1);
		OPERATOR_WEIGHT.put("/", 1);
		OPERATOR_WEIGHT.put("(", -1);
	}

	private boolean isOperator(String str) {
		return OPERATORS.contains(str);
	}

	private boolean isOpenBracket(String str) {
		return "(".equals(str);
	}

	private boolean isCloseBracket(String str) {
		return ")".equals(str);
	}

	private int compareOperatorWeight(String optr1, String optr2) {
		return operatorWeight(optr1) - operatorWeight(optr2);
	}

	private int operatorWeight(String operator) {
		return OPERATOR_WEIGHT.get(operator);
	}

	public List<String> infixToPostfix(List<String> infixTokens) {
		List<String> postfixExpression = new ArrayList<>();
		Stack<String> stack = new Stack<>();
		for (String token : infixTokens) {
			// 不是运算符，直接add到list中
			if (!isOperator(token)) {
				postfixExpression.add(token);
				continue;
			}

			// 当stack中没有运算符时或当前运算符为'('时，将当前运算符push到stack中
			if (stack.isEmpty() || isOpenBracket(token)) {
				stack.push(token);
				continue;
			}

			// 当当前运算符为')'时，从stack中pop出运算符直至pop出一个'('
			if (isCloseBracket(token)) {
				popUtilOpenBracket(postfixExpression, stack);
				continue;
			}

			String topOperator = stack.peek();
			// 当前运算符优先级大于stack顶部运算符优先级时，将当前运算符push到stack中
			if (compareOperatorWeight(topOperator, token) < 0) {
				stack.push(token);
			} else {
				// 从stack中pop出优先级大于等于当前运算符优先级的运算符
				popHeightWieghtOperator(postfixExpression, stack, topOperator);
				stack.push(token);
			}
		}
		// pop出stack中剩余的运算符
		while (!stack.isEmpty())
			postfixExpression.add(stack.pop());
		return postfixExpression;
	}

	public double caculate(List<String> postfixExpression) {
		Stack<Double> nums = new Stack<>();
		for (String token : postfixExpression) {
			if (isOperator(token)) {
				double b = nums.pop();
				double a = nums.pop();
				nums.push(caculate(a, b, token));
			} else {
				nums.push(Double.valueOf(token));
			}
		}
		return nums.pop();
	}

	private double caculate(double first, double second, String operator) {
		switch (operator) {
		case "+":
			return first + second;
		case "-":
			return first - second;
		case "*":
			return first * second;
		case "/":
			return first / second;
		}
		return first + second;
	}

	private void popUtilOpenBracket(List<String> postfixExpression, Stack<String> operators) {
		String token;
		while (!isOpenBracket((token = operators.pop()))) {
			postfixExpression.add(token);
		}
	}

	/**
	 * 从operators中pop出所有优先级大于等于operator的运算符
	 */
	private void popHeightWieghtOperator(List<String> postfixExpression, Stack<String> operators, String operator) {
		int weight = operatorWeight(operator);
		String o;
		while (!operators.isEmpty()) {
			o = operators.peek();
			if (operatorWeight(o) < weight)
				break;
			postfixExpression.add(operators.pop());
		}
	}

	public static void main(String[] args) {
		PostfixProcessor processor = new PostfixProcessor();
//		List<String> infix = new ArrayList<>(Arrays.asList("a", "-", "(", "b", "-", "c", ")"));
		List<String> infix = new ArrayList<>(Arrays.asList("6", "-", "1", "*", "3"));
		List<String> postfix = processor.infixToPostfix(infix);
		System.out.println(postfix);
		System.out.println("Result: " + processor.caculate(postfix));
	}
}
