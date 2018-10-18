/* @Cain
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxswingapplication1;

public interface DeckInterface {
    /** shuffles deck*/
    public void shuffle();
    
    /** clears deck*/
    public void clear();
    
    /** checks if the deck empty
     @return boolean*/
    public boolean isEmpty();
    
}
