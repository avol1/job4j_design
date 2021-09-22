package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted = 0;

        List<User> mergedList = new ArrayList<>();
        mergedList.addAll(current);
        mergedList.addAll(previous);

        HashMap<Integer, User> usersMap = new HashMap<>();

        Iterator<User> it = mergedList.iterator();

        while (it.hasNext()) {
            User user = it.next();

            if (previous.contains(user) && current.contains(user)) {
                User userInMap = usersMap.get(user.getId());
                if (userInMap == null) {
                    usersMap.put(user.getId(), user);
                    continue;
                }
                if (!userInMap.getName().equals(user.getName())) {
                    changed += 1;
                }
            } else if (previous.contains(user)) {
                deleted += 1;
            } else {
                added += 1;
            }
        }

        return new

                Info(added, changed, deleted);
    }

}
