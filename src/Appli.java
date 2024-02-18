class Appli {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                Modele modele = new Modele();
                Vue vue = new Vue(modele);
                ControlButton controlButton = new ControlButton(modele, vue);
                ControlMenu controlMenu = new ControlMenu(modele, vue);
            }
        });
    }
}
