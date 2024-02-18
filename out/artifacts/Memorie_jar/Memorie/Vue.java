import javax.swing.*;
import java.awt.*;

public class Vue extends JFrame {
    private Modele modele;
    private ControlMenu controlMenu;
    private ControlButton controlButton;

    private JMenuItem jMenuScores;
    private JMenuItem jMenuRejouer;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem3;

    private JLabel jLabelTemps;
    private JLabel jLabelVie;
    private JLabel jLabelValeurTemps;

    private JPanel cartes;

    private int sizeOfGrid;
    private Chrono chrono;
    private boolean chronoStarted = false; //False au départ de chaque partie


    public Vue(Modele modele) {
        this.modele = modele;
        this.setSizeOfGrid(4);
        this.setSize(getSizeOfGrid());
        this.setResizable(false);
        this.setTitle("Memory");
        this.iniAttribut();
        this.menu();
        this.affichage(getSizeOfGrid());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public Vue(Modele modele, int sizeOfGrid) {
        this.modele = modele;
        this.setSizeOfGrid(sizeOfGrid);
        this.setSize(sizeOfGrid);
        this.setResizable(false);
        this.setTitle("Memory");
        this.iniAttribut();
        this.menu();
        this.affichage(sizeOfGrid);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void changerDeVue(int sizeOfGrille) {
        this.reset();
        this.modele = new Modele(sizeOfGrille);
        this.setSizeOfGrid(sizeOfGrille);
        this.setSize(sizeOfGrille);
        this.setjlabelDoubleTemps();
        this.iniAttribut();
        this.menu();
        this.affichage(sizeOfGrille);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        revalidate();
        repaint();
        this.pack();
    }

    /**
     * Menu du jeux
     */
    public void menu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuOption = new JMenu("Options");
        JMenu jMenuNewGame = new JMenu("Nouvelle partie");
        jMenuScores = new JMenuItem("Meilleurs scores");
        this.jMenuRejouer = new JMenuItem("Rejouer");
        this.jMenuItem1 = new JMenuItem("3X3");
        this.jMenuItem2 = new JMenuItem("4X4");
        this.jMenuItem3 = new JMenuItem("5X5");

        this.jMenuScores.addActionListener(controlMenu);
        this.jMenuRejouer.addActionListener(controlMenu);
        this.jMenuItem1.addActionListener(controlMenu);
        this.jMenuItem2.addActionListener(controlMenu);
        this.jMenuItem3.addActionListener(controlMenu);
        jMenuNewGame.add(jMenuItem1);
        jMenuNewGame.add(jMenuItem2);
        jMenuNewGame.add(jMenuItem3);
        jMenuOption.add(jMenuNewGame);
        jMenuOption.add(this.jMenuRejouer);
        jMenuOption.add(jMenuScores);
        jMenuBar.add(jMenuOption);
        setJMenuBar(jMenuBar);
    }

    /**
     * Méthode initiation des attributs de la vue
     */
    public void iniAttribut() {
        if (sizeOfGrid == 3)
            modele.setNbVie(10);
        if (sizeOfGrid == 4)
            modele.setNbVie(15);
        if (sizeOfGrid == 5)
            modele.setNbVie(25);

        jLabelValeurTemps = new JLabel();
        if (jLabelValeurTemps.getText().equals(""))
            jLabelValeurTemps.setText("0,0");
        this.chrono = new Chrono(jLabelValeurTemps);

        this.controlMenu = new ControlMenu(modele, this);
        this.controlButton = new ControlButton(modele, this);
        this.jLabelTemps = new JLabel("Temps : ");
        this.jLabelVie = new JLabel("Vie restante : " + modele.getNbVie());
    }

    public void affichage(int sizeOfGrid) {
        JPanel game = new JPanel();
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        JPanel time = new JPanel();
        time.setLayout(new BoxLayout(time, BoxLayout.X_AXIS));
        cartes = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        JPanel vie = new JPanel();

        //Temps
        time.add(this.jLabelTemps);
        time.add(this.jLabelValeurTemps);

        //Cartes
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++){
            modele.getJButtons(i).addActionListener(controlButton);
            this.cartes.add(modele.getJButtons(i));
        }

        //Vie
        vie.add(this.jLabelVie);

        game.add(time);
        game.add(cartes);
        game.add(vie);

        this.setContentPane(game);
    }

    public void reset() {
        this.getContentPane().removeAll();
        this.chronoStarted = false;
        modele.resteModel();
    }

    /**
     * Vue meilleur score
     * @param error texte des meilleurs scores
     */
    public void dialogBestScore(String error) {
        JOptionPane.showMessageDialog(this, error, "Bests Scores", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Vue Gagnante
     */
    public void dialogWin() {
        JOptionPane.showMessageDialog(this, "Bravo tu as gagné(e)!", "Tu es gagnant(e)", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Vue perdante
     */
    public void dialogLose() {
        JOptionPane.showMessageDialog(this, "Dommage tu as perdu(e), Tu peux Réessayer!", "Oups tu as perdu", JOptionPane.ERROR_MESSAGE);
    }

    public void setSizeOfGrid(int sizeOfGrid) {
        this.sizeOfGrid = sizeOfGrid;
    }

    /**
     * régler la taille de la fenêtre en fonction de la taille de la grille
     * @param sizeOfGrid nombre d'élément d'une ligne de la grille de jeu de cartes
     */
    public void setSize(int sizeOfGrid) {
        if (sizeOfGrid == 4)
            this.setSize(500, 550);
        if (sizeOfGrid == 3)
            this.setSize(400, 450);
        if (sizeOfGrid == 5)
            this.setSize(600, 670);
    }

    public void startChrono() {
        this.chrono.start();
    }

    public void isWin() {
        if (modele.getIsWinning()) {
            stopChrono();
            modele.updateBestScore(this.jLabelValeurTemps.getText());
            dialogWin();
        }
    }

    public void setjlabelDoubleTemps() {
        this.jLabelValeurTemps.setText(null);
    }

    public void stopChrono() {
        this.chrono.terminate();
    }

    public int getSizeOfGrid() {
        return sizeOfGrid;
    }

    public JMenuItem getjMenuRejouer() {
        return jMenuRejouer;
    }

    public JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    public JMenuItem getjMenuItem2() {
        return this.jMenuItem2;
    }

    public JMenuItem getjMenuItem3() {
        return this.jMenuItem3;
    }

    public JMenuItem getjMenuScores() {
        return jMenuScores;
    }

    public JPanel getCartes() {
        return this.cartes;
    }

    public boolean isChronoStarted() {
        return chronoStarted;
    }

    public void setChronoStarted(boolean chronoStarted) {
        this.chronoStarted = chronoStarted;
    }

    public void updateVie(){
        this.jLabelVie.setText("Vie restante : " + modele.getNbVie());
        if (modele.getNbVie() < 1) {
            stopChrono();
            dialogLose();
            modele.setNbVie(1);
        }
    }
}