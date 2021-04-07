package invadem;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class ProjectileTest {


    @Test
    public void testProjectileConstruction() {
        Projectile proj = new Projectile(null, 1, 1, true, 0, 0, 1, 3);
        assertNotNull(proj);
    }

    @Test
    public void testProjectileIsFriendly() {
        Projectile proj = new Projectile(null, 1, 1, true, 0, 0, 1, 3);
        assertTrue(proj.isPlayer());
    }

    @Test
    public void testProjectileIsNotFriendly() {
        Projectile proj = new Projectile(null, 1, 1, false, 0, 0, 1, 3);
        assertFalse(proj.isPlayer());
    }

    @Test
    public void testProjectileIntersects() {
        Projectile proj = new Projectile(null, 1, 1, true, 16, 16, 16, 16);
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        Tank tank = new Tank(null, 16, 16, 16, 16);
        assertTrue(inv.checkCollision(proj));
        assertTrue(tank.checkCollision(proj));
    }
    
    @Test
    public void testProjectileNoIntersect() {
        Projectile proj = new Projectile(null, 1, 1, true, 0, 0, 1, 3);
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        Tank tank = new Tank(null, 16, 16, 22, 14);
        assertFalse(inv.checkCollision(proj));
        assertFalse(tank.checkCollision(proj));
    }
    
    @Test
    public void testProjectileDamage(){
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        List<Projectile> bullets = new ArrayList<Projectile>();
        bullets = inv.Shoot(new ArrayList<Projectile>(), null);
        assertEquals(1, bullets.get(0).getDMG());
    }
    
    @Test
    public void testProjectileDamagePower(){
        PowerInvader inv = new PowerInvader(null, 100, 16, 16, 16, 16);
        List<Projectile> bullets = new ArrayList<Projectile>();
        bullets = inv.Shoot(new ArrayList<Projectile>(), null);
        assertEquals(3, bullets.get(0).getDMG());
    }
    
    @Test
    public void testPlayerProjectileMovement(){
        //UPWARDS MOVEMENT
        Projectile proj = new Projectile(null, -1, 1, true, 0, 0, 1, 3);
        int position = proj.getY();
        proj.tick();
        assertEquals(position-1, proj.getY());
    }
    
    @Test
    public void testInvaderProjectileMovement(){
        //DOWNWARDS MOVEMENT
        Projectile proj = new Projectile(null, 1, 1, false, 0, 0, 1, 3);
        int position = proj.getY();
        proj.tick();
        assertEquals(position+1, proj.getY());
    }
    

}
