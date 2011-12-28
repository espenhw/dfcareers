package org.grumblesmurf.df.careers;

import static org.grumblesmurf.df.careers.Attribute.*;
import static org.grumblesmurf.df.careers.Evaluation.Unsuitable;

public enum Position
{
    Manager(AnalyticalAbility, Creativity, SocialAwareness, LinguisticAbility, Empathy, SocialAwareness) {
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.trait("Honesty").value < 40) {
                return Unsuitable;
            }
            if (dwarf.trait("Compromising").value < 40) {
                return Unsuitable;
            }

            return super.evaluate(dwarf);
        }
    },
    Broker(Intuition, Empathy, SocialAwareness, AnalyticalAbility, Memory, Intuition,
           LinguisticAbility, Empathy, SocialAwareness),
    CloseCombatDwarf(Strength, Agility, Toughness, Willpower, SpatialSense, KinaestheticSense),
    RangedCombatDwarf(Agility, Focus, SpatialSense, KinaestheticSense),
    Surgeon(Agility, Focus, SpatialSense, KinaestheticSense),
    Suturer(Agility, Focus, SpatialSense, KinaestheticSense),
    Socializer(LinguisticAbility, Empathy, SocialAwareness),
    Miner(Strength, Toughness, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Biter(Strength, Toughness, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Bowyer(Agility, Creativity, SpatialSense, KinaestheticSense),
    Engraver(Agility, Creativity, SpatialSense, KinaestheticSense),
    GemSetter(Agility, Creativity, SpatialSense, KinaestheticSense),
    BoneCarver(Agility, Creativity, SpatialSense, KinaestheticSense),
    Clothier(Agility, Creativity, SpatialSense, KinaestheticSense),
    StoneCrafter(Agility, Creativity, SpatialSense, KinaestheticSense),
    Weaver(Agility, Creativity, SpatialSense, KinaestheticSense),
    WoodCrafter(Agility, Creativity, SpatialSense, KinaestheticSense),
    Carpenter(Strength, Agility, Creativity, SpatialSense, KinaestheticSense),
    Woodcutter(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Swimmer(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Wrestler(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Mason(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Armorsmith(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    MetalCrafter(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Metalsmith(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Weaponsmith(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Glassmaker(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Leatherworker(Strength, Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    AnimalDissector(Agility, KinaestheticSense),
    Tanner(Agility, KinaestheticSense),
    FishDissector(Agility, KinaestheticSense),
    AnimalTrainer(Agility, Toughness, Endurance, Intuition, Patience, Empathy),
    Trapper(Agility, AnalyticalAbility, Creativity, SpatialSense),
    BoneDoctor(Strength, Agility, Focus, SpatialSense, KinaestheticSense),
    CrutchWalker(Agility, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Diagnostician(AnalyticalAbility, Memory, Intuition),
    Appraiser(AnalyticalAbility, Memory, Intuition),
    WoundDresser(Agility, SpatialSense, KinaestheticSense, Empathy),
    Brewer(Strength, Agility, KinaestheticSense),
    Butcher(Strength, Agility, Endurance, KinaestheticSense),
    Dyer(Strength, Agility, Endurance, KinaestheticSense),
    Grower(Strength, Agility, Endurance, KinaestheticSense),
    Milker(Strength, Agility, Endurance, KinaestheticSense),
    Miller(Strength, Agility, Endurance, KinaestheticSense),
    Thresher(Strength, Agility, Endurance, KinaestheticSense),
    CheeseMaker(Strength, Agility, Endurance, AnalyticalAbility, Creativity, KinaestheticSense),
    Cook(Agility, AnalyticalAbility, Creativity, KinaestheticSense),
    Herbalist(Agility, Memory, KinaestheticSense),
    LyeMaker(Strength, Toughness, Endurance, KinaestheticSense),
    PotashMaker(Strength, Toughness, Endurance, KinaestheticSense),
    WoodBurner(Strength, Toughness, Endurance, KinaestheticSense),
    FishCleaner(Agility, Endurance, KinaestheticSense),
    FisherDwarf(Strength, Agility, Focus, Patience, KinaestheticSense),
    FurnaceOperator(Strength, Toughness, Endurance, AnalyticalAbility, KinaestheticSense),
    GemCutter(Agility, AnalyticalAbility, SpatialSense, KinaestheticSense),
    StrandExctractor(Strength, Agility, Endurance, AnalyticalAbility, KinaestheticSense),
    Mechanic(Strength, Agility, Endurance, AnalyticalAbility, Creativity, SpatialSense),
    SiegeEngineer(Strength, Agility, Endurance, AnalyticalAbility, Creativity, SpatialSense),
    PumpOperator(Strength, Toughness, Endurance, Willpower, KinaestheticSense),
    SiegeOperator(Strength, Toughness, Endurance, AnalyticalAbility, Focus, SpatialSense),
    BuildingDesigner(AnalyticalAbility, Creativity, SpatialSense),
    Organizer(AnalyticalAbility, Creativity, SocialAwareness),
    RecordKeeper(AnalyticalAbility, Memory, Focus),
    Student(AnalyticalAbility, Memory, Focus),
    Dodger(Agility, Toughness, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Comedian(Agility, Creativity, KinaestheticSense, LinguisticAbility) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.trait("Neurosis").value > 75) {
                return Unsuitable;
            }
            return super.evaluate(dwarf);
        }
    },
    Intimidator(Agility, KinaestheticSense, LinguisticAbility) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.trait("Compromising").value > 60) {
                return Unsuitable;
            }
            return super.evaluate(dwarf);
        }
    },
    JudgeOfIntent(Intuition, Empathy, SocialAwareness),
    Liar(Creativity, LinguisticAbility, SocialAwareness) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.trait("Honesty").value > 39) {
                return Unsuitable;
            }
            return super.evaluate(dwarf);
        }
    },
    Observer(Intuition, Focus, SpatialSense);

    private final Attribute[] attributes;

    Position(Attribute... attributes) {
        this.attributes = attributes;
    }

    private static float sumScore(Dwarf dwarf, Attribute... attributes) {
        float pct = 0;
        for (Attribute attribute : attributes) {
            int x = dwarf.attribute(attribute.toString());
            pct += attribute.percentageFor(x);
        }
        return pct / attributes.length;
    }

    public Evaluation evaluate(Dwarf dwarf) {
        return Evaluation.of(sumScore(dwarf, attributes));
    }

    @Override
    public String toString() {
        String name = name();
        return name.replaceAll("(.)([A-Z])", "$1 $2");
    }
}
