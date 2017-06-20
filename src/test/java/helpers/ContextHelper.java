package helpers;

import builders.Users;

public class ContextHelper {

    private Users user;
    private String searchTerm;


    public Users getUser() {

        if (user == null) {
            user = new Users();
        }
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getSearchTerm() {

        if (searchTerm == null) {
            searchTerm = "";
        }
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
