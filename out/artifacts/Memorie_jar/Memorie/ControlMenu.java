import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {
    private Modele modele;
    private Vue vue;

    /**
     * Constructeur du ControlMenu
     * @param modele de l'appl
     * @param vue de l'appl
     */
    public ControlMenu(Modele modele, Vue vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getjMenuScores())
            vue.dialogBestScore(modele.getBestScores());
        if (e.getSource() == vue.getjMenuRejouer()){
            int holdSizeOfGrid = vue.getSizeOfGrid();
            vue.changerDeVue(holdSizeOfGrid);
        }
        if (e.getSource() == vue.getjMenuItem1())
            vue.changerDeVue(3);
        if (e.getSource() == vue.getjMenuItem2())
            vue.changerDeVue(4);
        if (e.getSource() == vue.getjMenuItem3())
            vue.changerDeVue(5);
    }
}
