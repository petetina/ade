package antoinepetetin.fr.emploidutemps.Models;

import java.util.List;

/**
 * Created by antoine on 17/12/16.
 */

public class Group {
    private String libelle;
    private int id;
    private boolean selected = false;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return libelle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
