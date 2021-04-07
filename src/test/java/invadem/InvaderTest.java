package invadem;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import invadem.SuperInvader;


public class InvaderTest {

    @Test
    public void testInvaderConstruction() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertNotNull(inv);

    }

    @Test
    public void testInvaderFireProjectile() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertNotNull(inv.Shoot(new ArrayList<Projectile>(), null));
    }
    
    @Test
    public void testArmorInvaderFireProjectile() {
        ArmorInvader inv = new ArmorInvader(null, 100, 16, 16, 16, 16);
        assertNotNull(inv.Shoot(new ArrayList<Projectile>(), null));
    }

    @Test
    public void testInvaderIsNotDead() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertEquals(false, inv.isDead());
    }

    @Test
    public void testInvaderIsDead() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        inv.hit();
        assertEquals(true, inv.isDead());
    }

    @Test
    public void testInvaderIntersectWithPlayerProjectile() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        Projectile proj = new Projectile(null, 1, 1, true, 16, 16, 16, 16); // THE BOOLEAN DETERMINES IF ITS A PLAYER PROJECTILE
        assertTrue(inv.checkCollision(proj));

    }

    @Test
    public void testInvaderNoIntersectWithPlayerProjectile() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        Projectile proj = new Projectile(null, 1, 1, true, 0, 0, 1, 3);
        assertFalse(inv.checkCollision(proj));

    }
    
    @Test
    public void testInvaderDamage() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertEquals(1,inv.getDMG());

    }

    @Test
    public void testPowerInvaderDamage() {
        PowerInvader inv = new PowerInvader(null, 100, 16, 16, 16, 16);
        assertEquals(3,inv.getDMG());

    }
    
    @Test
    public void testArmoredInvaderDamage() {
        ArmorInvader inv = new ArmorInvader(null, 100, 16, 16, 16, 16);
        assertEquals(1,inv.getDMG());

    }
    
    @Test
    public void testInvaderHealth() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertEquals(1,inv.getHealth());

    }
    
    @Test
    public void testPowerInvaderHealth() {
        PowerInvader inv = new PowerInvader(null, 100, 16, 16, 16, 16);
        assertEquals(1,inv.getHealth());

    }
    
    @Test
    public void testArmoredInvaderHealth() {
        ArmorInvader inv = new ArmorInvader(null, 100, 16, 16, 16, 16);
        assertEquals(3,inv.getHealth());

    }
    
    @Test
    public void testInvaderScore() {
        Invader inv = new Invader(null, 100, 16, 16, 16, 16);
        assertEquals(100,inv.getScore());

    }
    
    @Test
    public void testPowerInvaderScore() {
        PowerInvader inv = new PowerInvader(null, 100, 16, 16, 16, 16);
        assertEquals(250,inv.getScore());

    }
    
    @Test
    public void testArmoredInvaderScore() {
        ArmorInvader inv = new ArmorInvader(null, 100, 16, 16, 16, 16);
        assertEquals(250,inv.getScore());

    }
    
    @Test
    public void testInvadersWinExact(){
        //exactly meet victory requirements
       Invader inv = new Invader(null, 100, 16, 100, 16, 16);
       assertTrue(inv.invaderWin());
    }
    
    @Test
    public void testInvadersWinOver(){
        //10 pixels over victory requirements
       Invader inv = new Invader(null, 100, 16, 110, 16, 16);
       assertTrue(inv.invaderWin());
    }
    
    @Test
    public void testInvadersDontWin(){
        //victory requirements not met
       Invader inv = new Invader(null, 100, 16, 16, 16, 16);
       assertFalse(inv.invaderWin());
    }
    
    @Test
    public void testInvaderMove(){
        //Test Single Step
       Invader inv = new Invader(null, 100, 16, 16, 16, 16);
       int x = inv.getX();
       inv.tick();
       assertEquals(x-1, inv.getX());
    }
    

    
    

}
