/**
 * Esta clase define el elemento fisico Bola
 * @version 05/05/2014/Final
 * @author Daniel Veas, Matias Lacasia, Carlos Polanco
 */

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
   
   /**
    * Constructor de la clase Bola, que toma los datos y genera las bolas
    * @param mass Masa de la bola
    * @param radius Radio de la bola
    * @param position posicion actual donde se encuentra la bola
    * @param speed velocidad actual de la vola
    */
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      pos_t = position;
      speed_t = speed;
      this.radius = radius;
   }
   
   /**
    * Metodo que calcula los proximos valores de velocidad y posición dado un delta de tiempo.
    * El calculo va a depender si colisionan o no.
    * @param delta_t Intervalo delta de tiempo con el cual se trabajará para calcular los datos
    * @param world   Instanncia de MyWorld en la cual se encuentran los elementos fisicos a trabajar
    * @see 
    */
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

   /**
    * Metodo que actualiza los datos de posicion y velocidad con los datos calculados en ComputeNextState
    */
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   
   /** Metodo que calcula si hay colision entre dos objetos, en este caso son bolas.   
   * @param b Bola con la cual se desea probar la colision
   * @return True si existe colision y False si no existe colision
   */
   public boolean collide(Ball b) {
	  double distance = this.pos_t - b.pos_t;
	  double collision = Math.sqrt(distance*distance);
	  double radiusT = this.radius + b.radius;
	  if(collision <= radiusT){	
		  return true;
	  }
	  else return false;
    }
   
   /**
    * Metodo que entrega la descripcion del string
    * @return String con la id de la posicion y velocidad de una bola
    */
   public String getDescription() {
	   String line = null;
	   line = "Position_Ball"+ this.getId() + "\t"+ "Speed_Ball"+ this.getId();
	   return line;
   }	
   
   /**
    * Metodo que entrega el estado actual de la posicion y la velocidad.
    * @return String con la posicion y velocidad actual
    */
   public String getState(){
	   String line = null;
	   line = this.pos_t + "\t" + this.speed_t;
	   return line;
   }
   
   /**
    * Metodo que retorna la posicion actual de la bola
    * @return Posicion de la bola en el momento actual
    */
   public double getPosition(){
	   return pos_t;
   }
   
   /**
    * Metodo que retorna la velocidad actual de la bola
    * @return Velocidad de la bola en el momento actual
    */
   public double getSpeed() {
	   return speed_t;
    }
   
   /**
    * Metodo que retorna la masa de la bola
    * @return Masa de la bola
    */
   public double getMass(){
	   return mass;
   }
}