import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<Role> availRoles = new ArrayList<Role>(Arrays.asList(allRoles));

        for (int i = 0; i < availRoles.size(); i++) {
            Role role = availRoles.get(i);
            if (role.chosen) {
                availRoles.remove(role);
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