/* @ Cain
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxswingapplication1;
import java.util.Stack;

public class Deck implements DeckInterface{
    private int deckSize; //tracks deck size
    private Stack<Card> StackOfCards;
    
    public Deck (){ //default constructor that calls constructor(bool jokers)
        this(false);
    }
    
    public Deck(boolean jokers){ //constructor with bool paramater to track whether or not the deck needs jokers
        if(jokers == true){
            Stack<Card> tempDeck = new Stack<>();
            this.StackOfCards = tempDeck;
        }
        else{
            Stack<Card> tempDeck = new Stack<>();
            this.StackOfCards= tempDeck;
        }
        
    }
    
    // Creates Deck
    /* Change to push cards into the deck stack*/
    private Stack<Card> createDeck(boolean jokers){
        Stack<Card> save = new Stack<>();
        for(int i = 0; i < 4; i++){ //for loop for suit
            for(int j = 0; j < 13; j++){ //for loop for levels
                Card tempCard = new Card(i, j+1);
                //test
                System.out.print(tempCard.toString());
                System.out.println();
                
                save.push(tempCard);
            }
        }
        if(jokers == true){
            save.push(new Card(9000,9000));
            save.push(new Card(9000,9000));
            this.deckSize = 54;
            return save;
        }
        else{
            this.deckSize = 52;
            return save;
        }
    }
    
    
    //change to use pop()
    public Card draw(){
        Card drawnCard = null;
        System.out.println("Card drawn!");
        if(!StackOfCards.isEmpty()){
            drawnCard = StackOfCards.pop();        
        }
        
        return drawnCard;
    }
    
    /** Resets the deck in order, use clear() then create a new deck*/
    public void resetDeck(){
        clear();
        Stack<Card> newDeck;
        newDeck = createDeck(false);
        this.StackOfCards = newDeck;
    }
    
    /* cleares deck*/
    @Override
    public void clear(){
        while(!isEmpty()){
            StackOfCards.pop();
            deckSize--;
        }
    }
    
    /* removes one card from the deck, use pop and then change the card val to null*/
    public void remove(){
        if(!isEmpty()){
            StackOfCards.pop();
            deckSize--;                   
        }
    }
    
    
    /* This one is tricky, look back on your old notes to change the stack into an array
        then use the shuffling algorithm I made to shuffle the cards around
        push() them back into the deck after.
    */
    @Override
    public void shuffle(){
        //saved to array tempDeck
        Card[] tempDeck = toArray();

        //for-loop randomizes order of cards
        for ( int i = tempDeck.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = tempDeck[i];
            tempDeck[i] = tempDeck[rand];
            tempDeck[rand] = temp;
        }
        
        //puts cards back into deck stack
        for (Card i : tempDeck) {
            StackOfCards.push(i);
        }
       
    }
    
    @Override
    public boolean isEmpty(){
        return StackOfCards.isEmpty();
    }
    
    public Card[] toArray(){
        //@SuppressWarnings("Unchecked")//suppress warning for the array
        Card[] saveArray = new Card[deckSize];//create new array of objects
        int i = 0;
        while (!StackOfCards.isEmpty()){// pops values from stack and saves them in an array
            saveArray[i] = StackOfCards.pop();
            i++;
        }
        return saveArray;
    }
}
