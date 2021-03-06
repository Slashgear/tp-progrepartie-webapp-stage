package metier;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.List;

import persistance.DialogueBd;

import meserreurs.MonException;

public class Stage {

    private String id;
    private String libelle;
    private Date datedebut;
    private Date datefin;
    private int nbplaces;

    public int getNbplaces() {
        return nbplaces;
    }


    public void setNbplaces(int nbplaces) {
        this.nbplaces = nbplaces;
    }


    public int getNbinscrits() {
        return nbinscrits;
    }


    public void setNbinscrits(int nbinscrits) {
        this.nbinscrits = nbinscrits;
    }

    private int nbinscrits;


    public Stage(String id, String libelle, Date datedebut, Date datefin,
                 int nbplaces, int nbinscrits) {
        this.id = id;
        this.libelle = libelle;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.nbplaces = nbplaces;
        this.nbinscrits = nbinscrits;
    }


    public String getId() {
        return id;
    }

    public Stage() {

        // TODO Auto-generated constructor stub
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

	
	/* traitements m�tier */

    public void insertionStage() throws MonException {

        try {
            String mysql = "";
            DateFormat dateFormatpers = new SimpleDateFormat("yyyy-MM-dd");
            String dd = dateFormatpers.format(this.getDatedebut());
            String df = dateFormatpers.format(this.getDatefin());


            mysql = "INSERT INTO stages (id, libelle, datedebut ,";
            mysql = mysql + " datefin, nbplaces, nbinscrits) ";
            mysql = mysql + " VALUES ( \'" + this.getId() + "\', \'" + this.getLibelle() + "\', ";
            mysql = mysql + "\' " + dd + "\', " + "\' " + df + "\', ";
            mysql = mysql + this.getNbplaces() + ", " + this.getNbinscrits() + " )";
            DialogueBd.insertionBD(mysql);
        } catch (MonException e) {
            throw e;
        }
    }

    public List<Stage> rechercheLesStages() throws MonException, ParseException {
        List<Object> rs;
        List<Stage> mesStages = new ArrayList<Stage>();
        int index = 0;
        try {
            String mysql = "";

            mysql = "SELECT * FROM stages ORDER BY id ASC";

            rs = DialogueBd.lecture(mysql);

            while (index < rs.size()) {
                // On cr�e un stage
                Stage unS = new Stage();
                // il faut redecouper la liste pour retrouver les lignes
                unS.setId(rs.get(index + 0).toString());
                unS.setLibelle(rs.get(index + 1).toString());
                DateFormat dateFormatpers = new SimpleDateFormat("yyyy-MM-dd");
                unS.setDatedebut(dateFormatpers.parse(rs.get(index + 2).toString()));
                unS.setDatefin((dateFormatpers.parse(rs.get(index + 3).toString())));
                unS.setNbplaces(Integer.parseInt(rs.get(index + 4).toString()));
                unS.setNbinscrits(Integer.parseInt(rs.get(index + 5).toString()));
                // On incr�mente tous les 6 champs
                index = index + 6;
                mesStages.add(unS);
            }

            return mesStages;

        } catch (MonException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public List<Stage> recherche(String name) throws MonException, ParseException {
        List<Object> rs;
        List<Stage> mesStages = new ArrayList<Stage>();
        int index = 0;
        try {
            String mysql = "";

            mysql = "SELECT * FROM stages WHERE libelle LIKE '%" + name + "%' ORDER BY id ASC";

            rs = DialogueBd.lecture(mysql);

            while (index < rs.size()) {
                // On cr�e un stage
                Stage unS = new Stage();
                // il faut redecouper la liste pour retrouver les lignes
                unS.setId(rs.get(index + 0).toString());
                unS.setLibelle(rs.get(index + 1).toString());
                DateFormat dateFormatpers = new SimpleDateFormat("yyyy-MM-dd");
                unS.setDatedebut(dateFormatpers.parse(rs.get(index + 2).toString()));
                unS.setDatefin((dateFormatpers.parse(rs.get(index + 3).toString())));
                unS.setNbplaces(Integer.parseInt(rs.get(index + 4).toString()));
                unS.setNbinscrits(Integer.parseInt(rs.get(index + 5).toString()));
                // On incr�mente tous les 6 champs
                index = index + 6;
                mesStages.add(unS);
            }

            return mesStages;

        } catch (MonException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void supprimerStage(int id) throws MonException {
        String mysql = "DELETE FROM stages WHERE id=" + id + ";";
        try {
            DialogueBd.insertionBD(mysql);
        } catch (MonException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void findStageById(int id) throws ParseException, MonException {

        String mysql = "SELECT * FROM stages WHERE id=" + id + ";";
        try {
            List<Object> rs = DialogueBd.lecture(mysql);
            if (rs.size() == 6) {
                this.setId(rs.get(0).toString());
                this.setLibelle(rs.get(1).toString());
                DateFormat dateFormatpers = new SimpleDateFormat("yyyy-MM-dd");
                this.setDatedebut(dateFormatpers.parse(rs.get(2).toString()));
                this.setDatefin((dateFormatpers.parse(rs.get(3).toString())));
                this.setNbplaces(Integer.parseInt(rs.get(4).toString()));
                this.setNbinscrits(Integer.parseInt(rs.get(5).toString()));
            } else {
                throw new MonException("Duplicate id", "Identifiant du stage non valide");
            }
        } catch (MonException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getDateDebutToString() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy,MM,dd");
        return dateformat.format(this.datedebut);
    }

    public String getDateFinToString() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy,MM,dd");
        return dateformat.format(this.datefin);
    }

    public boolean isExistingId() throws MonException {
        String mysql = "SELECT id FROM stages WHERE id=" + this.id + ";";
        List<Object> rs = DialogueBd.lecture(mysql);
        if(rs.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void modifierStage() throws MonException {
        try {
            String mysql = "";
            DateFormat dateFormatpers = new SimpleDateFormat("yyyy-MM-dd");
            String dd = dateFormatpers.format(this.getDatedebut());
            String df = dateFormatpers.format(this.getDatefin());

            mysql = "UPDATE stages";
            mysql = mysql + " SET libelle = '" + this.getLibelle() + "',";
            mysql = mysql + " datedebut = '" + dd + "',";
            mysql = mysql + " datefin = '" + df + "',";
            mysql = mysql + " nbplaces = '" + this.getNbplaces() + "',";
            mysql = mysql + " nbinscrits = '" + this.getNbinscrits() + "'";
            mysql = mysql + " WHERE id = " + this.getId();

            DialogueBd.insertionBD(mysql);
        } catch (MonException e) {
            throw e;
        }
    }
}
