package in.buka.app.libs.models;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class Project {

    public String projectImage;
    public String projectCategory, projectName, projectDesc;
    public int backers, funded;
    public long deadline;

    public String backers() {
        return Integer.toString(backers);
    }

    public String funded() {
        return Integer.toString(funded) + "%";
    }
}
