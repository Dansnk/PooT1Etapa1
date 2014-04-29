import java.util.*;

public class Ball extends PhysicsElement {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     // current position at time t
   private double pos_tPlusDelta;  // next position in delta time in future
   private double speed_t;   // speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   
   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
   
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      pos_t = position;
      speed_t = speed;
      this.radius = radius;
   }

   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
        speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t + speed_tPlusDelta*delta_t;;
     } else {
        speed_tPlusDelta = speed_t;
        pos_tPlusDelta = pos_t + speed_t*delta_t;
     }
   }
   
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
    
   public boolean collide(Ball b) {
	  double distance = this.pos_t - b.pos_t;
	  double collision = Math.sqrt(distance*distance);
	  double radiusT = this.radius + b.radius;
	  if(collision <= radiusT){	
		  return true;
	  }
	  else return false;
    }
   
   public String getDescription() {
	   String line = null;
	   line = "Position_Ball"+ id + "\t"+ "Speed_Ball"+ id;
	   return line;
   
   }	
   
   public String getState(){
	   String line = null;
	   line = this.pos_t + "\t" + this.speed_t;
	   return line;
   }
   
   public double getRadius() {
	   return radius;
   }
	   
   public double getPosition(){
	   return pos_t;
   }
   
   public double getSpeed() {
	   return speed_t;
    }
   
   public double getMass(){
	   return mass;
   }

}