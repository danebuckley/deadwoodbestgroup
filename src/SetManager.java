
public class SetManager {
    private Set[] sets;


    void assignRoleTo(Player player, Role role) {

    }

    private Role[] getRoles(Set set) {
        return set.getRoles();
    }


    // Scoring and Resetting

    public void itsAWrap() {

    }

    public void resetScenes() {

    }


    // Utilities

    Role[] getRoleOptions(Player player) {
        return getRoles(player.currentSet);
    }

    String[] rolesAsStrings(Role[] roles) {
        String[] strings = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            strings[i] = roles[i].name;
        }
        return strings;
    }
}