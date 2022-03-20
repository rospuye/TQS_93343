package tqs;

public class Address {
    
    private String road;
    private String state;
    private String cirty;
    private String zio;
    private String houseNumber;

    public Address(String road, String state, String cirty, String zio, String houseNumber) {
        this.road = road;
        this.state = state;
        this.cirty = cirty;
        this.zio = zio;
        this.houseNumber = houseNumber;
    }

    public String toString() {
        return this.road + ", " + this.state + ", " + this.cirty + ", " + this.zio + ", " + this.houseNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cirty == null) ? 0 : cirty.hashCode());
        result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
        result = prime * result + ((road == null) ? 0 : road.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((zio == null) ? 0 : zio.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (cirty == null) {
            if (other.cirty != null)
                return false;
        } else if (!cirty.equals(other.cirty))
            return false;
        if (houseNumber == null) {
            if (other.houseNumber != null)
                return false;
        } else if (!houseNumber.equals(other.houseNumber))
            return false;
        if (road == null) {
            if (other.road != null)
                return false;
        } else if (!road.equals(other.road))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (zio == null) {
            if (other.zio != null)
                return false;
        } else if (!zio.equals(other.zio))
            return false;
        return true;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCirty() {
        return cirty;
    }

    public void setCirty(String cirty) {
        this.cirty = cirty;
    }

    public String getZio() {
        return zio;
    }

    public void setZio(String zio) {
        this.zio = zio;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
