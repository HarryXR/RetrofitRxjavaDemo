<<<<<<< HEAD:app/src/main/java/com/harry/refresh/SwipeRefreshLayoutDirection.java
package com.harry.refresh;

/**
 * Created by oliviergoutay on 1/23/15.
 */
public enum SwipeRefreshLayoutDirection {

    TOP(0),
    BOTTOM(1),
    BOTH(2),
    NONE(3);

    private int mValue;

    SwipeRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipeRefreshLayoutDirection getFromInt(int value) {
        for (SwipeRefreshLayoutDirection direction : SwipeRefreshLayoutDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
=======
package com.harry.refresh;

/**
 * Created by oliviergoutay on 1/23/15.
 */
public enum SwipeRefreshLayoutDirection {

    TOP(0),
    BOTTOM(1),
    BOTH(2),
    NONE(3);

    private int mValue;

    SwipeRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipeRefreshLayoutDirection getFromInt(int value) {
        for (SwipeRefreshLayoutDirection direction : SwipeRefreshLayoutDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
>>>>>>> b21739f8016bf954db46b47da3b0383cfd98620a:app/src/main/java/com/harry/refresh/SwipeRefreshLayoutDirection.java
