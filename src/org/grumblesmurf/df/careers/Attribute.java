package org.grumblesmurf.df.careers;

public enum Attribute
{
    Strength(PercentageCalculator.Plus),
    Agility(PercentageCalculator.Minus),
    Toughness(PercentageCalculator.Plus),
    Endurance(PercentageCalculator.Average),
    Recuperation(PercentageCalculator.Average),
    DiseaseResistance(PercentageCalculator.Average),
    Willpower(PercentageCalculator.Average),
    Memory(PercentageCalculator.Plus),
    Focus(PercentageCalculator.DoublePlus),
    Intuition(PercentageCalculator.Average),
    Patience(PercentageCalculator.Plus),
    Empathy(PercentageCalculator.Average),
    SocialAwareness(PercentageCalculator.Average),
    Creatvity(PercentageCalculator.Plus),
    Musicality(PercentageCalculator.Average),
    AnalyticalAbility(PercentageCalculator.Plus),
    LinguisticAbility(PercentageCalculator.Average),
    SpatialSense(PercentageCalculator.DoublePlus),
    KinaestheticSense(PercentageCalculator.Average);

    private final PercentageCalculator calc;

    Attribute(PercentageCalculator calc) {
        this.calc = calc;
    }

    public float percentageFor(int x) {
        return calc.percentageFor(x);
    }

    private enum PercentageCalculator
    {
        DoublePlus {
            @Override
            float percentageFor(int x) {
                if (x <= 1199) {
                    return Math.round((0.0333988 * x) - 23.3792);
                } else if (x >= 1200 && x <= 1399) {
                    return Math.round((0.0837487 * x) - 83.8315);
                } else if (x >= 1400 && x <= 1499) {
                    return Math.round((0.168333 * x) - 202.333);
                } else if (x >= 1500 && x <= 1599) {
                    return Math.round((0.168343 * x) - 202.515);
                } else if (x >= 1600 && x <= 1799) {
                    return Math.round((0.0837538 * x) - 67.339);
                } else {
                    return Math.round((0.0238412 * x) + 40.4198);
                }
            }
        },
        Plus {
            @Override
            float percentageFor(int x) {
                if (x <= 949) {
                    return Math.round((0.0333988 * x) - 15.0295);
                } else if (x >= 950 && x <= 1149) {
                    return Math.round((0.0837487 * x) - 62.8943);
                } else if (x >= 1150 && x <= 1249) {
                    return Math.round((0.168333 * x) - 160.249);
                } else if (x >= 1250 && x <= 1349) {
                    return Math.round((0.168343 * x) - 160.429);
                } else if (x >= 1350 && x <= 1549) {
                    return Math.round((0.0837538 * x) - 46.4006);
                } else {
                    return Math.round((0.0238412 * x) + 46.3801);
                }
            }
        },
        Average {
            @Override
            float percentageFor(int x) {
                if (x <= 749) {
                    return Math.round((0.030357 * x) - 6.0714);
                } else if (x >= 750 && x <= 899) {
                    return Math.round((0.111852 * x) - 67.2223);
                } else if (x >= 900 && x <= 999) {
                    return Math.round((0.168333 * x) - 118.166);
                } else if (x >= 1000 && x <= 1099) {
                    return Math.round((0.168343 * x) - 118.343);
                } else if (x >= 1100 && x <= 1299) {
                    return Math.round((0.0837538 * x) - 25.4621);
                } else {
                    return Math.round((0.0238412 * x) + 52.3404);
                }
            }
        },
        Minus {
            @Override
            float percentageFor
                (
                    int x) {
                if (x <= 599) {
                    return Math.round((0.0370356 * x) - 5.51835);
                } else if (x >= 600 && x <= 799) {
                    return Math.round((0.0837487 * x) - 33.5822);
                } else if (x >= 800 && x <= 899) {
                    return Math.round((0.168333 * x) - 101.333);
                } else if (x >= 900 && x <= 999) {
                    return Math.round((0.168343 * x) - 101.509);
                } else if (x >= 1000 && x <= 1099) {
                    return Math.round((0.168343 * x) - 101.676);
                } else {
                    return Math.round((0.0417669 * x) + 37.3904);
                }
            }
        };

        abstract float percentageFor(int x);
    }
}
