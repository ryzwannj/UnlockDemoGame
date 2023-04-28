package JavaFX_Board.CardObject;

public class ClickableZone {
    
    private static final Integer RADIUS=18;
    
    private final int numero;
    private final double x_center_location;
    private final double y_center_location;
    
    public ClickableZone(int numero, double x, double y) {
        this.numero=numero;
        this.x_center_location=x;
        this.y_center_location=y;
    }
    
    public Integer getNumero() {
        return numero;
    }
    
    public boolean isInside(double x,double y) {
        
       return Math.sqrt(Math.pow((x-x_center_location), 2)+
                        Math.pow((y-y_center_location), 2))
                        <= RADIUS;
    
    }
}
