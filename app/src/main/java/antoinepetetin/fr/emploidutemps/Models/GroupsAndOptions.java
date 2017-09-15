package antoinepetetin.fr.emploidutemps.Models;

import java.util.List;

/**
 * Created by antoine on 17/12/16.
 */

public class GroupsAndOptions {
    private List<Group> groupes;
    private List<Group> options;

    public List<Group> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Group> groupes) {
        this.groupes = groupes;
    }

    public List<Group> getOptions() {
        return options;
    }

    public void setOptions(List<Group> options) {
        this.options = options;
    }
}
