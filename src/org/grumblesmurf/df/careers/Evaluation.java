package org.grumblesmurf.df.careers;

public enum Evaluation
{
    Superb(90),
    VeryGood(75) {
        @Override
        public String toString() {
            return "Very good";
        }
    },
    Good(60),
    Reasonable(49),
    Subpar(39),
    Poor(24),
    VeryPoor(9) {
        @Override
        public String toString() {
            return "Very good";
        }
    },
    Horrible(Integer.MIN_VALUE),
    Unsuitable(Integer.MAX_VALUE);

    private final float floor;

    Evaluation(float floor) {
        this.floor = floor;
    }

    public static Evaluation of(float score) {
        for (Evaluation evaluation : values()) {
            if (score > evaluation.floor) {
                return evaluation;
            }
        }
        return Unsuitable;
    }
}
