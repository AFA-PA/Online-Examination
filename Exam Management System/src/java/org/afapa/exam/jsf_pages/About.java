package org.afapa.exam.jsf_pages;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named("about")
@ApplicationScoped
public class About implements Serializable {

    public static final Member ABUZER = new Member("Abuzer", "Nasir", "ugr/22553/13");
    public static final Member ABDULAHI = new Member("Abdulahi", "Taju", "ugr/22517/13");
    public static final Member PETROS = new Member("Petros", "Lakew", "ugr/22596/13");
    public static final Member ABDELA = new Member("Abdela", "Nuru", "ugr/22561/13");
    public static final Member FASIKA = new Member("Fasika", "Zergaw", "ugr/23653/13");

    public static final Member[] members = new Member[]{
        About.ABUZER, About.FASIKA, About.ABDULAHI, About.PETROS, About.ABDELA
    };

    @Setter
    @Getter
    public static class Member {

        String firstName;
        String lastName;
        String id;

        public Member(String firstName, String lastName, String id) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.id = id;
        }
    }

    public List<Member> getMembers() {
        return Arrays.asList(members);
    }
}
