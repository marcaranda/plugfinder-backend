package backend.plugfinder.models;

public class ClientModel extends UserModel {
    /** Position in the ranking */
    int rank;
    /** Points that defines the position in the ranking */
    int points;
    /** saved co2*/
    float co2;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getCo2() {
        return co2;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }
}
