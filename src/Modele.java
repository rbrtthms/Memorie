import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;


public class Modele {
    private ArrayList<ImageIcon> listeImage;
    private String SCORE_FILE;
    private ImageIcon imageIconBack;
    private ArrayList<JButton> jButtons;
    private int nbCarteRetourner = 1;
    private Icon iconToucher;
    private int buttonNbToucher;
    private boolean isWinning;
    private int nbVie;
    private float[] bestScores;


    /**
     * Constructeur default
     */
    public Modele() {
        final int defaultSizeOfGrille = 4;
        this.jButtons = new ArrayList<>();
        this.imageIconBack = new ImageIcon("src/Asset/Back.jpg");
        this.imageIconBack = new ImageIcon(this.imageIconBack.getImage().getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH));
        int nbGroupeImage = defaultSizeOfGrille*2;
        this.listeImage = new ArrayList<ImageIcon>();
        this.bestScores = new float[3];
        lectureImages(nbGroupeImage);
        setSCORE_FILE("4");
        setImageButton(defaultSizeOfGrille);
    }

    /**
     * Constructeur avec une taille précise de la grille
     * @param sizeOfGrid taille de la grille
     */
    public Modele(int sizeOfGrid) {
        this.listeImage = new ArrayList<ImageIcon>();
        this.jButtons = new ArrayList<>();
        this.imageIconBack = new ImageIcon("src/Asset/Back.jpg");
        this.imageIconBack = new ImageIcon(this.imageIconBack.getImage().getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH));
        this.bestScores = new float[3];
        if (sizeOfGrid == 4) {
            int nbGroupeImage = sizeOfGrid*2;
            lectureImages(nbGroupeImage);
            setSCORE_FILE("4");
        } else if (sizeOfGrid == 3) {
            int nbGroupeImage = (sizeOfGrid*2)-1;
            lectureImages(nbGroupeImage);
            setSCORE_FILE("3");
        } else if (sizeOfGrid == 5){
            int nbGroupeImage = 13;
            lectureImages(nbGroupeImage);
            setSCORE_FILE("5");
        }
        setImageButton(sizeOfGrid);
    }

    /**
     * Enregistre un boolean a true si la partie est gagné
     * @param i nombre de carte retournée sur la grille en court
     */
    public void isWinning(int i) {
        this.nbCarteRetourner += i;
        if (this.nbCarteRetourner >= jButtons.size())
            this.isWinning = true;
        else
            this.isWinning = false;
    }

    /**
     * Boucle du jeu poor détecter tout les cas dans la grille de jeux
     * @param icon image de la carte cliqué
     * @param i numéro du button cliqué
     */
    public void game(Icon icon, int i) {
        if (this.nbCarteRetourner == jButtons.size()) {
            System.out.println("Rejouer please");
        } else if (this.iconToucher == null) {
            this.iconToucher = icon;
            this.buttonNbToucher = i;
        } else if (jButtons.get(i) == jButtons.get(this.buttonNbToucher)) {
        } else if (this.iconToucher == icon) {
            isWinning(2);
            this.iconToucher = null;
        } else if (this.iconToucher != icon){
            this.nbVie --;
            returnCard(i);
            returnCard(this.buttonNbToucher);
            this.iconToucher = null;
        } else
            System.out.println("Erreur: comportement non prévu");
    }

    /**
     * Retourne une carte dans la grille avec un point d'interrogation
     * @param i position du bouton a retourner
     */
    public void returnCard(int i) {
        jButtons.get(i).setIcon(imageIconBack);
    }

    public void turnCard(int i) {
        jButtons.get(i).setIcon(this.jButtons.get(i).getRolloverIcon());
        game(this.jButtons.get(i).getRolloverIcon(), i);
    }

    public void setImageButton(int sizeOfGrid) {
        int a = 0;
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++) {
            jButtons.add( new JButton(imageIconBack));
            jButtons.get(i).setRolloverIcon(listeImage.get(a % listeImage.size()));
            jButtons.get(i).setRolloverEnabled(false); //true => superpower
            jButtons.get(i).setName(i+"button");
            a++;
        }
        melanger();
    }

    public void melanger() {
        for (int i = 0; i < 5; i++)
            Collections.shuffle(this.jButtons);
    }

    public void lectureImages(int sizeOfGrille){
        String IMAGE_FILE = "src/Image";
        for (int j = 0; j < sizeOfGrille; j++) {
            this.listeImage.add( new ImageIcon(IMAGE_FILE +"/"+ (j+1) + ".jpg"));
            this.listeImage.set(j, new ImageIcon(this.listeImage.get(j).getImage().getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH)));
        }
    }

    public void updateBestScore(String score) {
        getBestScores();
        score = score.replace(",","." );
        for (int i = 0; i < bestScores.length ; i++ ) {
            if (Float.parseFloat(score) < this.bestScores[i]) {
                this.bestScores[i] = Float.parseFloat(score);
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(this.SCORE_FILE));
                    bw.write(""+bestScores[0]);
                    bw.newLine();
                    bw.write(""+bestScores[1]);
                    bw.newLine();
                    bw.write(""+bestScores[2]);
                    bw.newLine();
                    bw.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * Créer le texte a afficher pour les meilleurs scores
     * @return retour les 3 meilleurs scores sous forme de texte
     */
    public String getBestScores(){
        getBestScore();
        String scores = "Best Scores :";
        String newLine = System.getProperty("line.separator");
        scores += newLine;
        scores += "  1 - " + this.bestScores[0] + newLine;
        scores += "  2 - " + this.bestScores[1] + newLine;
        scores += "  3 - " + this.bestScores[2] + newLine;
        return scores;
    }

    /**
     * Récupère les 3 scores du fichier concerné
     */
    public void getBestScore() {
        String Best_Scores;
        try{
            int cpt = 0;
            BufferedReader br = new BufferedReader(new FileReader(this.SCORE_FILE));
            while ((Best_Scores = br.readLine()) != null) {
                this.bestScores[cpt] = Float.parseFloat(Best_Scores);
                cpt++;
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error !! getBEstScore");
        }
    }


    public void resteModel() {
        this.jButtons = null;
        this.listeImage = null;
        this.nbCarteRetourner = 1;
        this.iconToucher = null;
        this.buttonNbToucher = 0;
        this.isWinning = false;
    }

    public JButton getJButtons(int i) {
        return jButtons.get(i);
    }

    /**
     * crée le path pour trouver le fichier des scores enregistré
     * @param level niveau de la grille
     */
    public void setSCORE_FILE(String level) {
        this.SCORE_FILE = "src/Scores/Best_Scores_Size"+level+".txt";
    }

    public boolean getIsWinning() {
        return this.isWinning;
    }

    public void setNbVie(int nbVie) {
        this.nbVie = nbVie;
    }

    public int getNbVie() {
        return this.nbVie;
    }
}