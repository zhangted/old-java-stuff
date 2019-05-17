//9/27/17

package zhangTask7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class zhangTask7 {

	public static void main(String args[]) {
		String userInp;
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		do {
			
			System.out.println("\nMenu: \n");
			System.out.println("1) Evaluate a postfix expression");
			System.out.println("2) Convert infix expression to postfix expression and evaluate");
			System.out.println("3) Read words from text file into a linked list and evaluate time spent \n   to display words (using iterators and get(i) method)");
			System.out.println("4) Exit \n");
			System.out.println("Choose an option (1-4):  ");
			choice = scan.nextInt();
			switch(choice) {
				
			case 1:
				scan.nextLine();
				System.out.println("Enter postfix expression: ");
				userInp = scan.nextLine();
				System.out.println(postFixEval(userInp));
				break;
			case 2:
				scan.nextLine();
				System.out.println("Enter infix expression: ");
				userInp = scan.nextLine();
				String postFixConvert = inFixToPostFix(userInp);
				System.out.println("PostFix Notation: " + postFixConvert);
				System.out.println("Evaluated: " + postFixEval(postFixConvert));
				break;
				/*System.out.println(inFixToPostFix("(9/(4+5-6))*(5-4)*9"));
				System.out.println(inFixToPostFix("5+2*3"));
				System.out.println(inFixToPostFix("((9/(3-(1+1)))*3)-(2+1+1)"));
				System.out.println(inFixToPostFix("3+2*3-6")));*/
				
			case 3:
				scan.nextLine();
				System.out.println("Enter the name of the text file: (hangman.txt) ");
				String filename = "src/" + scan.nextLine();
				
				LinkedList<String> words = new LinkedList<String>();
				
				try(BufferedReader bufReader = new BufferedReader(new FileReader(filename))) {
					String currentLine;
					while((currentLine = bufReader.readLine()) != null) { //while the file has text
					words.add(currentLine); //add each line into a linked list
					}
				} catch(IOException e) { //error
					e.printStackTrace();
				}
				
				Set<String> usedWords = new HashSet<String>();
				int wordsPerLine = 0;
				
				//iterator traversal begins 
				long start = System.nanoTime();
				ListIterator<String> iterator = words.listIterator(words.size());
				while(iterator.hasPrevious()) {
					String currentWord = iterator.previous();
					if(!usedWords.contains(currentWord)) {
						usedWords.add(currentWord);
						wordsPerLine += 1;
						System.out.print(currentWord + "	");
						if(wordsPerLine == 4) {
							System.out.println();
							wordsPerLine = 0;
						}
					}
				}
				long end = System.nanoTime();
				long iteratorTime = end-start;
				//iterator traversal ends and time is calculated
				
				//reset the set and the wordsperline counter and display a break between the 2 traversals
				usedWords = new HashSet<String>();
				wordsPerLine = 0;
				System.out.println("-----------------------------------------------------------------------------------------\n\n");
				
				
				//get() traversal begins
				start = System.nanoTime();
				wordsPerLine = 0;
				for(int x = words.size()-1; x >= 0; x--) {
					String currentWord = words.get(x);
					if(!usedWords.contains(currentWord)){
						usedWords.add(currentWord);
						wordsPerLine += 1;
						System.out.print(currentWord + "	");
						if(wordsPerLine == 4) {
							System.out.println();
							wordsPerLine = 0;
						}
					}
				}
				end = System.nanoTime();
				long getTime = end-start;
				//get traversal ends and runtime is calculated
				
				
				System.out.println("\nIterator traversal execution time: " + (iteratorTime)/1000000. + " ms");
				System.out.println("\nget() traversal execution time: " + (getTime)/1000000. + " ms");
				
				break;
			case 4:
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} while (choice != 4);
		scan.close();
	}
	
	public static Integer postFixEval(String input) {
		Stack<Integer> stack = new Stack<Integer>();
		for(int x = 0; x < input.length(); x++) {
			Character cur = input.charAt(x);
			if(Character.isDigit(cur)) { //add numbers to stack
				stack.push(Character.getNumericValue(input.charAt(x)));
			}
			else if(Character.isLetter(cur)) {
				return -1;
			}
			else { //pop 2 numbers,do the operation on those numbers, store in k
				Integer i = stack.pop();
				Integer j = stack.pop();
				Integer k = 0;
				
				if(input.charAt(x) == '+') {
					k = i + j;
				}
				if(input.charAt(x) == '*') {
					k = i * j;
				}
				if(input.charAt(x) == '/') {
					if(i > j) {
						k = i / j;
					}
					else {
						k = j / i;
					}
				}
				if(input.charAt(x) == '-') {
					if(i > j) {
						k = i - j;
					}
					else {
						k = j - i;
					}
				}
				stack.push(k);
			}
		}
		return stack.pop();
	}
	
	
	public static int priority(Character a) { //power > multip./division > add/subtract
		if(a == '*' || a == '/') {
			return 2;
		}
		if(a == '+' || a == '-') {
			return 1;
		}
		if(a == '^') {
			return 3;
		}
		return 0;
	}
	
	public static String inFixToPostFix(String input) {
		//4 possible character types -> (1) digits (2) outer parenthesis (3) closing parenthesis (4) operations
		String result = "";
		Stack<Character> symbols = new Stack<Character>();
		for(int i = 0; i < input.length(); i++) {
			Character cur = input.charAt(i);
			if(Character.isDigit(cur)) { //add digits to the output string
				result += cur;
			}
			else if(cur == '(') {  
				symbols.push('(');
			}
			else if(cur == ')') {
				while(symbols.isEmpty() == false && symbols.peek() != '(') { //add all elements before the open parenthesis to the output string 
					result += symbols.pop();
				}
				if(symbols.peek() == '(') {
					symbols.pop();
				}
			}
			else {
				while(symbols.isEmpty() == false && priority(symbols.peek()) >= priority(cur)) { //the last stored operator has higher precedence than the current operator 
					result += symbols.pop(); //so it has to be added to output string now
				}
				symbols.push(cur);//add current operator to the stack
			}
		}
		while(symbols.isEmpty() == false) { //if there are any leftover operators
			result += symbols.pop(); //add them to the output string
		}
		return result;
	}
	
	
	
}
