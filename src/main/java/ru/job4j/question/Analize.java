package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted = 0;

        HashSet<User> mergedSet = new HashSet<>();
        mergedSet.addAll(current);
        mergedSet.addAll(previous);

        Iterator<User> it = mergedSet.iterator();

        while (it.hasNext()) {
            User user = it.next();

            if (previous.contains(user) && current.contains(user)) {
                User prevUser = previous.stream()
                        .filter(prev -> prev.equals(user))
                        .findFirst()
                        .orElse(null);
                if (!Objects.equals(user.getName(), prevUser.getName())) {
                    changed += 1;
                }
            } else if (previous.contains(user)) {
                deleted += 1;
            } else {
                added += 1;
            }
        }

        return new Info(added, changed, deleted);
    }

}
