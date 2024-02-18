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
        this.vue = vue;
        init(vue, modele);
    }

    public void init(Vue vue, Modele modele) {
        this.modele = modele;
        vue.getjMenuItem1().addActionListener(this);
        vue.getjMenuItem2().addActionListener(this);
        vue.getjMenuItem3().addActionListener(this);
        vue.getjMenuRejouer().addActionListener(this);
        vue.getjMenuScores().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vue.getjMenuScores()) {
            vue.dialogBestScore(modele.getBestScores());
        }
        if (e.getSource() == vue.getjMenuRejouer()){
            int holdSizeOfGrid = vue.getSizeOfGrid();
            vue.changerDeVue(holdSizeOfGrid);
            init(vue, vue.getModele());
        }
        if (e.getSource() == vue.getjMenuItem1()) {
            vue.changerDeVue(3);
            init(vue, vue.getModele());
        }
        if (e.getSource() == vue.getjMenuItem2()) {
            vue.changerDeVue(4);
            init(vue, vue.getModele());
        }
        if (e.getSource() == vue.getjMenuItem3()) {
            vue.changerDeVue(5);
            init(vue, vue.getModele());
        }
    }
}
