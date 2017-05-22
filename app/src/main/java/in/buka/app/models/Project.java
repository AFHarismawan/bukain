package in.buka.app.models;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class Project {

    public String image, video;
    public String category, name, desc;
    public long funded, modal;
    public int backers;
    public long deadline;

    public String backers() {
        return Integer.toString(backers);
    }

    public int fundedPercent() {
        return (int) (modal / funded) * 100;
    }

    public String funded() {
        return Integer.toString(fundedPercent()) + "%";
    }
}
