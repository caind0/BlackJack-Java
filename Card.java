/* @ Cain 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxswingapplication1;


import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//import java.io.File;
   

public class Card extends Parent{
    
        private final int suit;
        private final int level;
        
        public Card(int suit, int level){
            this.suit = suit;
            this.level = level;
            
            String filePath = String.format("/resources/%d-%d.gif", level, suit+1);
            System.out.printf("\n\n%s\n\n", filePath);
            
            Rectangle bg = new Rectangle(80, 100);
            bg.setArcWidth(20);
            bg.setArcHeight(20);
            bg.setFill(Color.WHITE);
            
            //should create card image according to the card data
            Image iCard = new Image(filePath);
            ImageView iView = new ImageView(iCard);
            
            Text text = new Text(toString());
            text.setWrappingWidth(70);

            getChildren().add(new StackPane(bg, text, iView));
        }
        
        public int getSuit(){
            return suit;
        }
        public int getLevel(){
            return level;
        }
        
        @Override
        public String toString(){
            String stringSuit, stringRank;
            
            if(suit == 0){stringSuit = "Spades";}
            else if (suit == 1) {stringSuit = "Hearts";}
            else if (suit == 2) {stringSuit = "Diamonds";}
            else if (suit == 3) {stringSuit = "Clubs";}
            else {stringSuit = "Jokers"; }
            
            // checks rank
            if(level == 1){stringRank = "Ace";}
            else if (level == 11) {stringRank= "Jack";}
            else if (level == 12) {stringRank = "Queen";}
            else if (level == 13) {stringRank = "King";}
            else {stringRank = String.format("%d", level); }
            
            return String.format("%s of %s", stringRank, stringSuit);
        }
        
        
    
}
