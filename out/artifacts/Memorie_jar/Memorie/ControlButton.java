import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton implements ActionListener {
    private Modele modele;
    private Vue vue;


    public ControlButton(Modele modele, Vue vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //chrono
        if(!vue.isChronoStarted()){
            vue.startChrono();
            vue.setChronoStarted(true);
        }

        //button
        for (int i = 0; i < vue.getSizeOfGrid()*vue.getSizeOfGrid(); i++){
            if (e.getSource() == vue.getCartes().getComponent(i))
                modele.turnCard(i);
        }
        vue.updateVie();
        vue.isWin();
    }
}