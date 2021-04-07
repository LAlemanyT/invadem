/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author luisa
 */
public class Invader extends SuperInvader{
    private List<PImage> imgs;
    private int win;
    int state;
    int velX;
    int velY;
    boolean horizontal;
    boolean move;
    int movedX;
    int movedY;
    
    
    
    Invader(List<PImage> imgs, int win, int x, int y, int width, int height){
        super(imgs, 1, win, 1, x, y, width, height);
        this.score = 100;
        
    }
    
    public List<Projectile> Shoot(List<Projectile> bulls, PImage img){
        bulls.add(new Projectile(img, 1, this.damage, false ,x+8, y,1,3));
        return bulls;
    }
    
    
    
    
    
}
