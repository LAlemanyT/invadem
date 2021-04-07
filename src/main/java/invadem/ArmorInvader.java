/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invadem;

import java.util.List;
import processing.core.PImage;

/**
 *
 * @author luisa
 */
public class ArmorInvader extends SuperInvader{
    
    
    ArmorInvader(List<PImage> imgs, int win, int x, int y, int width, int height){
        super(imgs, 1, win, 3, x, y, width, height);
        this.score = 250;
    }
    
    public List<Projectile> Shoot(List<Projectile> bulls, PImage img){
        bulls.add(new Projectile(img, 1, this.damage, false ,x+8, y,1,3));
        return bulls;
    }
    
    
}
