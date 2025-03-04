package com.no_country.fichaje.datos.organizacion;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Redes {

private String instagram;
private String facebook;
private String twitter;

    public Redes() { }

    public Redes(String facebook, String instagram, String twitter) {
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
    }

    public String getFacebook() { return facebook; }
    public void setFacebook(String facebook) { this.facebook = facebook; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getTwitter() { return twitter; }
    public void setTwitter(String twitter) { this.twitter = twitter; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Redes)) return false;
        Redes redes = (Redes) o;
        return Objects.equals(facebook, redes.facebook) &&
                Objects.equals(instagram, redes.instagram) &&
                Objects.equals(twitter, redes.twitter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facebook, instagram, twitter);
    }
}
