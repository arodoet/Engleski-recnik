package com.example.teodora.mojrecnik;

public class Rec {

    private String zadataRec;
    private String definicijaZadateReci;

    public Rec(String zadataRec, String definicijaZadateReci) {
        this.zadataRec = zadataRec;
        this.definicijaZadateReci = definicijaZadateReci;
    }

    public String getZadataRec() {
        return zadataRec;
    }

    public String getDefinicijaZadateReci() {
        return definicijaZadateReci;
    }


}
