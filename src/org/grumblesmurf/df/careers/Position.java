package org.grumblesmurf.df.careers;

import static org.grumblesmurf.df.careers.Attribute.*;
import static org.grumblesmurf.df.careers.Evaluation.Unsuitable;

public enum Position
{
    Manager(AnalyticalAbility, Creatvity, SocialAwareness, LinguisticAbility, Empathy, SocialAwareness) {
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.getTrait("STRAIGHTFORWARDNESS").value() < 40) {
                return Unsuitable;
            }
            if (dwarf.getTrait("COOPERATION").value() < 40) {
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
    Bowyer(Agility, Creatvity, SpatialSense, KinaestheticSense),
    Engraver(Agility, Creatvity, SpatialSense, KinaestheticSense),
    GemSetter(Agility, Creatvity, SpatialSense, KinaestheticSense),
    BoneCarver(Agility, Creatvity, SpatialSense, KinaestheticSense),
    Clothier(Agility, Creatvity, SpatialSense, KinaestheticSense),
    StoneCrafter(Agility, Creatvity, SpatialSense, KinaestheticSense),
    Weaver(Agility, Creatvity, SpatialSense, KinaestheticSense),
    WoodCrafter(Agility, Creatvity, SpatialSense, KinaestheticSense),
    Carpenter(Strength, Agility, Creatvity, SpatialSense, KinaestheticSense),
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
    Trapper(Agility, AnalyticalAbility, Creatvity, SpatialSense),
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
    CheeseMaker(Strength, Agility, Endurance, AnalyticalAbility, Creatvity, KinaestheticSense),
    Cook(Agility, AnalyticalAbility, Creatvity, KinaestheticSense),
    Herbalist(Agility, Memory, KinaestheticSense),
    LyeMaker(Strength, Toughness, Endurance, KinaestheticSense),
    PotashMaker(Strength, Toughness, Endurance, KinaestheticSense),
    WoodBurner(Strength, Toughness, Endurance, KinaestheticSense),
    FishCleaner(Agility, Endurance, KinaestheticSense),
    FisherDwarf(Strength, Agility, Focus, Patience, KinaestheticSense),
    FurnaceOperator(Strength, Toughness, Endurance, AnalyticalAbility, KinaestheticSense),
    GemCutter(Agility, AnalyticalAbility, SpatialSense, KinaestheticSense),
    StrandExctractor(Strength, Agility, Endurance, AnalyticalAbility, KinaestheticSense),
    Mechanic(Strength, Agility, Endurance, AnalyticalAbility, Creatvity, SpatialSense),
    SiegeEngineer(Strength, Agility, Endurance, AnalyticalAbility, Creatvity, SpatialSense),
    PumpOperator(Strength, Toughness, Endurance, Willpower, KinaestheticSense),
    SiegeOperator(Strength, Toughness, Endurance, AnalyticalAbility, Focus, SpatialSense),
    BuildingDesigner(AnalyticalAbility, Creatvity, SpatialSense),
    Organizer(AnalyticalAbility, Creatvity, SocialAwareness),
    RecordKeeper(AnalyticalAbility, Memory, Focus),
    Student(AnalyticalAbility, Memory, Focus),
    Dodger(Agility, Toughness, Endurance, Willpower, SpatialSense, KinaestheticSense),
    Comedian(Agility, Creatvity, KinaestheticSense, LinguisticAbility) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.getTrait("SELF_CONSCIOUSNESS").value() > 75) {
                return Unsuitable;
            }
            return super.evaluate(dwarf);
        }
    },
    Intimidator(Agility, KinaestheticSense, LinguisticAbility) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.getTrait("COOPERATION").value() > 60) {
                return Unsuitable;
            }
            return super.evaluate(dwarf);
        }
    },
    JudgeOfIntent(Intuition, Empathy, SocialAwareness),
    Liar(Creatvity, LinguisticAbility, SocialAwareness) {
        @Override
        public Evaluation evaluate(Dwarf dwarf) {
            if (dwarf.getTrait("STRAIGHTFORWARDNESS").value() > 39) {
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
    
    public static Position fromString(String s) {
        return valueOf(s.replaceAll("(.) (.)", "$1$2"));
    }
}
