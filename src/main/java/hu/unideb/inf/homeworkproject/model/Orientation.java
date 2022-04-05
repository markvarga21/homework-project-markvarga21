package hu.unideb.inf.homeworkproject.model;

/**
 * An enum representing the player's selection orientation,
 * which can be either {@code HORIZONTAL} or {@code VERTICAL}
 * or {@code NOT_CALIBRATED} (which is used if the player
 * had selected only one node - the first node in particular).
 */
public enum Orientation {
    /**
     * Player selection made in a {@code HORIZONTAL} way,
     * so the circles are in a row.
     */
    HORIZONTAL,

    /**
     * Player selection made in a {@code VERTICAL} way,
     * so the circles are in a row.
     */
    VERTICAL,

    /**
     * It is used when a player selects the first circle.
     */
    NOT_CALIBRATED
}
