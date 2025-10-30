/*
Reverse each word in a string
Write a function to reverse each word in a string.

Description :- A method has to be created that takes a string as input and extracts each word from that string and then reverse each word individually and gives the output as a reversed string. 

Example :-

Input:- great learning

output :- taerg gninrael

input :- hello guys how are you

output :- olleh syug woh era uoy
*/

import java.util.*;
public class Main {

    public static void main(String[] args) {

        //write your answer here
        Scanner sc=new Scanner(System.in);
        String s="Great Learning";
        String[] words = s.split(" ");
        StringBuilder res=new StringBuilder();
        for(String word:words){
            StringBuilder sb=new StringBuilder(word);
            res.append(sb.reverse()).append(" ");
        } 
        System.out.println(res.toString().trim());
        sc.close();
        
    }
}

