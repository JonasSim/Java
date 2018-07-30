/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotMachine;

import java.util.*;
/**
 *
 * @author w1565407
 */
public class Reel {

    private Symbol[] symbolList = new Symbol[6];

    private Random random = new Random();

    //Stores the image
    private String[] images = {"src\\bell.png", "src\\cherry.png", "src\\lemon.png", "src\\plum.png", "src\\redseven.png", "src\\watermelon.png"};

    /**
     * Creates a Reel with 6 random symbols
     */
    public Reel() {
        Set<Integer> generated = new LinkedHashSet<>();
        // To store 6 random numbers that are not  duplicated - Set do not duplicate
        while (generated.size() < 6) {// Need only 6 random numbers
            generated.add(random.nextInt(6));// Generate a random number between
            // 0 and 5
        }

        List<Integer> numbers = new ArrayList<Integer>();// Put the numbers into
        // a list to shuffle
        Iterator<Integer> generatedIt = generated.iterator();

        while (generatedIt.hasNext()) {
            numbers.add(generatedIt.next());
        }

        Collections.shuffle(numbers);//Shuffles the random numbers
        for (int i = 0; i < numbers.size(); i++) {
            Symbol symbol = new Symbol();
            symbol.setValue(numbers.get(i));
            symbol.setImage(images[i]);
            symbolList[i] = symbol;
        }

    }

   
    public Symbol[] reel() {
        return this.symbolList;
    }

}