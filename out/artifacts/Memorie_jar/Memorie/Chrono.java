import javax.swing.*;
import java.text.DecimalFormat;

public class Chrono extends Thread {
    JLabel temps;
    boolean start;

    Chrono(JLabel temps){
        this.temps=temps;
    }

    public void run(){
        start = true;
        float t = 0;

        while (start) {
            try{
                this.sleep(100);
                t += 0.1;
                DecimalFormat df = new DecimalFormat("#######0.0");
                String str = df.format(t);
                temps.setText(str);
            }
            catch(InterruptedException e){
                System.out.println("Catch Class Chrono");
            }
        }
    }

    public void terminate(){
        start = false;
    }
}