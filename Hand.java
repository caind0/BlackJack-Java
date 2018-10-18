/* @ Cain
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxswingapplication1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.Stack;


public class Hand{
    //holds and evaluates card values through this stack
    private Stack<Card> cardsInHand;
    //holds card visuals in this Javafx linked list 
    private ObservableList<Node> observableCards;
    
    private SimpleIntegerProperty chipCount;
    private SimpleIntegerProperty valueOfHand;
        
    public Hand(boolean dealer, ObservableList<Node> Cards){
        if(!dealer){
            this.observableCards = Cards;
            this.cardsInHand = new Stack<>();
            this.valueOfHand = new SimpleIntegerProperty(0);
            this.chipCount = new SimpleIntegerProperty(500);
        }
        else{
            this.observableCards = Cards;
            this.cardsInHand = new Stack<>();
            this.valueOfHand = new SimpleIntegerProperty(0);
            this.chipCount = new SimpleIntegerProperty(90000000);
        }       
    }

    //alternate way of checks current cardValue of hand
    public int evaluateCardValue(){
        //return value
        int sum = 0;
        
        //tempHand to hold cards drawn
        Stack<Card> tempHand = new Stack<>();

        //pops cards and adds up their value, pushes them into a temporary hand
        while(!cardsInHand.isEmpty()){
            Card tempCard = cardsInHand.pop();
            System.out.printf("Level = %d // Suit = %d\n", tempCard.getLevel(), tempCard.getSuit());
            
            //checks royals
            if(tempCard.getLevel() >= 10) { sum = sum + 10;}  
            //checks for aces 
            else if(tempCard.getLevel()==1) {
                if( (sum + 11) > 21) //if the sum is less than 11
                    //else ace value is 11
                    sum = sum + 1;
                else 
                    sum = sum + 11;
            }
            //checks numberssum = sum + tempCard.getLevel();
            else {sum = sum + tempCard.getLevel(); }
            
            //pushes card into tempHand
            tempHand.push(tempCard);
        }
        
        //puts cards back into hand
        while(!tempHand.isEmpty()){
            cardsInHand.push(tempHand.pop());
        }
        System.out.printf("Sum = %d\n", sum);
        //returns card value as int sum
        return sum;
    }
    //sets new value for Hand
    public void setValue(){
        this.valueOfHand.set(evaluateCardValue()); //uses method set in SimpleIntProperty to change the valueOfHand
    }
    
    //adds card to hand
    public void drawCard(Card newCard){
        System.out.println("Card added to hand!");
     
        observableCards.add(newCard); //adds card visual values into linked list
        cardsInHand.push(newCard); //pushes card number value into stack
    }
    
    public SimpleIntegerProperty cardValueProperty(){
        return valueOfHand;
    }
    
    //sets chip count for the loser
    public void setChipCount(int chipsLost, Hand winner){
        this.chipCount.set(this.chipCount.intValue() - chipsLost);
        winner.setChipCount(chipsLost);
    }
    //sets chip count for the winner
    public void setChipCount(int chipsWon){
        this.chipCount.set(this.chipCount.intValue() + chipsWon);
    }
    //returns chipCount
    public SimpleIntegerProperty getChipCount(){
        return chipCount;
    }
    
    //resets hand by clearing handStack and setting hand value to 0
    public void resetHand(){
        this.valueOfHand.set(0);
        this.observableCards.clear();
        clear();
    }
    
    //checks if empty
    public boolean isEmpty(){
        return cardsInHand.isEmpty();
    }
    
    //clears hand by popping all cards
    public void clear(){
        while(!isEmpty()){ 
            //get rid of card values by popping them and setting it to null
            Card tempCard = cardsInHand.pop();
            tempCard = null;
        }
    }
}
