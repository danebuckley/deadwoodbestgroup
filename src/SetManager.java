import java.util.ArrayList;

// The ideal place for digging into scenes and roles that have been assigned to a set. (probably)
// This class debatably has the most expansive job in our program.

public class SetManager {
    private Set[] sets;
    int wrapCount;

    void assignRoleTo(Player player, Role role) {
        player.role = role;
        player.working = true;
        role.chosen = true;
    }

    private Role[] getRoles(Set set) {
        System.out.println(set.name);
        return set.getRoles();
    }


    // Scoring and Resetting

    public void itsAWrap(Player player) {
        //payout
        player.working = false;
        player.practiceTokens = 0;
        wrapCount++;
    }

    public int getWrapCount(){
        return wrapCount;
    }


    // Utilities

    Role[] getRoleOptions(Player player) {
        Set currSet = (Set) player.currentArea;
        Role[] allRoles = getRoles(currSet);

        ArrayList<Role> takenRoles = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            Role role = players.get(i).role;
            if (role != null) {
                takenRoles.add(role);
            }
        }

        ArrayList<Role> availRoles = new ArrayList<>();
        for (int i = 0;  i < allRoles.length; i++) {
            Role role = allRoles[i];
            if (!takenRoles.contains(role)) {
                availRoles.add(role);
            }
        }
        
        return availRoles.toArray(new Role[availRoles.size()]);
    }

    String[] rolesAsStrings(Role[] roles) {
        String[] strings = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            strings[i] = roles[i].name;
        }
        return strings;
    }
}