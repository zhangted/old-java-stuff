
package zhangTask6;

import java.util.Scanner;

public class zhangTask6 {

	private static int calls;
	private static long start, end;
	
	public static void main(String args[]) {
		System.out.println("Test a function:\n");
		System.out.println("(1) Permutations");
		System.out.println("(2) Hexidecimal to Decimal");
		System.out.println("(3) Binary to Decimal");
		System.out.println("(4) Decimal to Hexadecimal\n");
		System.out.println("Select an option (1-4): ");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch(choice) {
			case 1:
				calls = 0;
				//Get inputs
				System.out.println("This function uses recursion, which returns an exponential # of outputs based on input size.\nPlease set a limit on string length for input (Highly recommended limit: 4): ");
				int limit = scan.nextInt();
				while(limit > 6) {
					System.out.println("Please enter an integer less than 7: ");
					limit = scan.nextInt();
				}
				scan.nextLine();
				System.out.println("Please enter the string: ");
				String inp = scan.nextLine();
				int strLength = inp.length();
				while(strLength > limit) {
					System.out.println("String must be " + limit + " characters max.");
					System.out.println("Please enter another string: ");
					inp = scan.nextLine();
					strLength = inp.length();
				}
				//Run program
				start = System.nanoTime();
				displayPermutation(inp);
				end = System.nanoTime();
				System.out.println("Function called " + calls + " times.");
				System.out.println("Execution Time: " + (end-start)/1000000. + " ms");
				break;
			case 2:
				calls = 0;
				//Get inputs
				System.out.println("Set a limit to input length (Recommended: 6 chars): ");
				int limit2 = scan.nextInt();
				while(limit2 > 6) {
					System.out.println("Please enter an integer less than 7: ");
					limit2 = scan.nextInt();
				}
				scan.nextLine();
				System.out.println("Please enter the string: ");
				String inp2 = scan.nextLine();
				int strLength2 = inp2.length();
				while(strLength2 > limit2) {				
					System.out.println("String must be " + limit2 + " characters max.");
					System.out.println("Please enter another string: ");
					inp2 = scan.nextLine();
					strLength = inp2.length();
				}
				//Run porgram
				start = System.nanoTime();
				System.out.println(hex2Dec(inp2));
				end = System.nanoTime();
				System.out.println("Function called " + calls + " times.");
				System.out.println("Execution Time: " + (end-start)/1000000. + " ms");
				break;
			case 3:
				calls = 0;
				//get inputs
				System.out.println("Set a limit to input length (Recommended: 10 chars): ");
				int limit3 = scan.nextInt();
				while(limit3 > 10) {
					System.out.println("Please enter an integer less than 10: ");
					limit3 = scan.nextInt();
				}
				scan.nextLine();
				System.out.println("Please enter the string: ");
				String inp3 = scan.nextLine();
				int strLength3 = inp3.length();
				while(strLength3 > limit3) {				
					System.out.println("String must be " + limit3 + " characters max.");
					System.out.println("Please enter another string: ");
					inp3 = scan.nextLine();
					strLength3 = inp3.length();
				}
				//run program
				start = System.nanoTime();
				System.out.println(bin2Dec(inp3));
				end = System.nanoTime();
				System.out.println("Function called " + calls + " times.");
				System.out.println("Execution Time: " + (end-start)/1000000. + " ms");
				break;
			case 4:
				calls = 0;
				//get inputs
				scan.nextLine();
				System.out.println("Please enter an integer: ");
				int inp4 = scan.nextInt();
				//run program
				start = System.nanoTime();
				System.out.println(dec2Hex(inp4));
				end = System.nanoTime();
				System.out.println("Function called " + calls + " times.");
				System.out.println("Execution Time: " + (end-start)/1000000. + " ms");
				break;
		}
		scan.close();
		
	}
	
	//Convert decimal # to hexadecimal #
	public static String dec2Hex(int num) {
		calls++;
		if(num < 10) { //If input is less than 10
			return String.valueOf((char)(num + 48)); //return character form of integer
		}
		else if(num > 9 && num < 16) { //If input is  10-15
			return String.valueOf((char)(num + 55)); //return character form 10-15 (A-F)
		}
		
		String toAdd; //the single hex digit to add
		double hex = ((num/16.)- (num/16)) * 16; //get the values to the right of the decimal point and * 16 to get the hex value

		if(hex < 10) { //If hex is between 0-9
			toAdd = String.valueOf((char)(hex + 48)); //Add character form of integer
		}
		else { //If hex is 10-16
			toAdd = String.valueOf((char)(hex + 55)); //Add character form of 10-15 (A-F)
		}
		return dec2Hex(num/16) + toAdd;	//keep calling dec2hex until the original number is 0-15, then use the first if-else clause to end the recursion
	}
	
	//Convert binary # to decimal #
	public static int bin2Dec(String binaryString) {
		calls++;
		if (binaryString.length() > 0) { //If the string still has values in it
			char left = binaryString.charAt(0); //Get the left most character
			binaryString = binaryString.substring(1,binaryString.length()); //Discard the first character in the binary String
			return (int) ((int)(left-48) * Math.pow(2, binaryString.length())) + bin2Dec(binaryString); // (2^length) * left digit + (repeat on shortened string until there are no more chars left))
		}
		return 0;
	}
	
	//Convert hexadecimal # to decimal #
	public static int hex2Dec(String hexString) {
		calls++;
		if(hexString.length() > 0) { //If the string still has values in it
			char left = hexString.charAt(0); //Get the left most character
			hexString = hexString.substring(1, hexString.length()); //Discard the first character in the hex string
			int multiplier = 0; //The characters can be 0-9 or A-F, so we have to convert them to integers before doing math
			if((int)(left) > 64 && (int)(left) < 71) { //If the char is A-F
				multiplier = (int) (left-65) + 10; //multiplier is 10-15
			}
			else if((int)(left) > 96 && (int)(left) < 103) { //If char is a-f
				multiplier = (int) (left-97) + 10; //multiplier is 10-15
			}
			else if((int)(left) > 47 && (int)(left) < 58){ //char is 0-9
				multiplier = (int) (left-48); //multiplier is 0-9
			}
			else {

			}
			return (int) (multiplier * Math.pow(16, hexString.length())) + hex2Dec(hexString); //keep calling function until no more chars left in hec2dex
		}
		return 0;
	}
	
	//Display all permutations of string s
	public static void displayPermutation(String s) { //Simplify the call to the function
		displayPermutation("", s);
	}
	public static void displayPermutation(String s1, String s2) { //letters are added to s1 until s2 becomes an empty string
		calls++;
		if(s2.length() == 0) {
			System.out.println(s1);
		}
		else {
			for(int i = 0; i < s2.length(); i++) { 
				//example case "abc"
				//for each letter in the string, move the "first letter" of a permutation to s1, then do a split for the remaining 
				//letters and move the first letter in the split array over to s1, and again...until there are no more letters left, which triggers a print.
				//then we backtrack to the initial split (right after the first letter was "shifted" to s1), and then shift the next letter to s1 and then
				//shift remaining letters over (repeatedly). when no more letters are left to iterate over (from the very first split),
				//we go back to the original call to the for loop and iterate and repeat all of what we did for the first letter, except using the next letter as 
				//the first letter in a permutation, until the all the characters have had a chance to be the first letter in a permutation
				//example: a[bc] -> [ab]c -> [abc] -> print -> a[bc] -> [ac]b -> [acb] -> print -> b[ac] -> [ba]c -> [bac] -> print -> b[ac] -> [bc]a -> 
				// [bca] -> print -> c[ab] -> [ca]b -> [cab] -> print -> c[ab] -> [cb]a -> [cba] -> print -> end initial call 
				displayPermutation(s1 + s2.charAt(i), s2.substring(0, i) + s2.substring(i+1,s2.length())); 
			}
		}
	}
	
	/*
	public static boolean isInteger(Object inp) {
		return inp instanceof Integer;
	}*/
	
	
	
}
