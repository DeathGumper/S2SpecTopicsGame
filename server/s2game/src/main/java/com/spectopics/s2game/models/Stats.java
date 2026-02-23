package com.spectopics.s2game.models;

import lombok.Data;

@Data
public class Stats {
    private float maxHealth = -1;
    private float health = -1;
    private float strength = -1;
    private float defense = -1;
    private float dexterity = -1;
    private float speed = -1;
    private float accuracy = -1;

    //Health
    public boolean SetHealth(float hp) {
        /*
        Sets health to the passed in hp value. Ensures hp is greater than or equal to -1 and does not exceed maxHealth

        Returns true if set. Otherwise return false.
         */
        if (hp >= -1 && hp <= maxHealth) {
            health = hp;
            return true;
        }
        return false;
    }

    public boolean AdjustHealth(float hp) {
        /*
        Adds hp to health. Ensures health doesn't go below 0 or exceed maxHealth

        Returns true if the full value was added. Otherwise returns false
         */
        if (health + hp >= 0 && health + hp <= maxHealth) {
            health += hp;
            return true;
        } else if (health + hp > maxHealth) { 
            health = maxHealth;
            return false;
        } else {
            health = 0;
            return false;
        }
    }

    //Strength
    public boolean SetStrength(float str) {
        /*
        Sets strength to the passed in str value. Ensures str is greater than or equal to -1

        Returns true if set. Otherwise return false.
         */
        if (str >= -1) {
            strength = str;
            return true;
        }
        return false;
    }
    
    public boolean AdjustStrength(float str) {
        /*
        Adds str to strength. Ensures strength doesn't go below 0

        Returns true if the full value was added. Otherwise returns false
         */
        if (strength + str >= 0) {
            strength += str;
            return true;
        } else {
            strength = Math.Max(0, strength + str);
            return false;
        }
    }

    //Defense
    public boolean SetDefense(float def) {
        /*
        Sets defense to the passed in def value. Ensures def is greater than or equal to -1

        Returns true if set. Otherwise return false.
         */
        if (def >= -1) {
            defense = def;
            return true;
        }
        return false;
    }
    
    public boolean AdjustDefense(float def) {
        /*
        Adds def to defense. Ensures defense doesn't go below 0

        Returns true if the full value was added. Otherwise returns false
         */
        if (defense + def >= 0) {
            defense += def;
            return true;
        } else {
            defense = Math.Max(0, defense + def);
            return false;
        }
    }

    //Dexterity
    public boolean SetDexterity(float dex) {
        /*
        Sets dexterity to the passed in dex value. Ensures dex is greater than or equal to -1

        Returns true if set. Otherwise return false.
         */
        if (dex >= -1) {
            dexterity = dex;
            return true;
        }
        return false;
    }
    
    public boolean AdjustDexterity(float dex) {
        /*
        Adds dex to dexterity. Ensures dexterity doesn't go below 0

        Returns true if the full value was added. Otherwise returns false
         */
        if (dexterity + dex >= 0) {
            dexterity += dex;
            return true;
        } else {
            dexterity = Math.Max(0, dexterity + dex);
            return false;
        }
    }

    //Speed
    public boolean SetSpeed(float spd) {
        /*
        Sets speed to the passed in spd value. Ensures spd is greater than or equal to -1

        Returns true if set. Otherwise return false.
         */
        if (spd >= -1) {
            speed = spd;
            return true;
        }
        return false;
    }
    
    public boolean AdjustSpeed(float spd) {
        /*
        Adds spd to speed. Ensures speed doesn't go below 0

        Returns true if the full value was added. Otherwise returns false
         */
        if (speed + spd >= 0) {
            speed += spd;
            return true;
        } else {
            speed = Math.Max(0, speed + spd);
            return false;
        }
    }

    //Accuracy
    public boolean SetAccuracy(float acc) {
        /*
        Sets accuracy to the passed in acc value. Ensures acc is greater than or equal to -1

        Returns true if set. Otherwise return false.
         */
        if (acc >= -1) {
            accuracy = acc;
            return true;
        }
        return false;
    }
    
    public boolean AdjustAccuracy(float acc) {
        /*
        Adds acc to accuracy. Ensures accuracy doesn't go below 0

        Returns true if the full value was added. Otherwise returns false
         */
        if (accuracy + acc >= 0) {
            accuracy += acc;
            return true;
        } else {
            accuracy = Math.Max(0, accuracy + acc);
            return false;
        }
    }

}
