import java.util.Random;

class Player {
    private int health;
    private int strength;
    private int attack;

    public Player(int health, int strength, int attack) {
        this.health = health;
        this.strength = strength;
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getAttack() {
        return attack;
    }

    public void reduceHealth(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int rollDie() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
}

class MagicalArena {
    private Player playerA;
    private Player playerB;

    public MagicalArena(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public void startMatch() {
        Player attacker = playerA.getHealth() < playerB.getHealth() ? playerA : playerB;
        Player defender = attacker == playerA ? playerB : playerA;

        while (playerA.isAlive() && playerB.isAlive()) {
            int attackRoll = attacker.rollDie();
            int defendRoll = defender.rollDie();

            int damage = attackRoll * attacker.getAttack();
            int defendedDamage = defendRoll * defender.getStrength();
            int actualDamage = Math.max(0, damage - defendedDamage);

            defender.reduceHealth(actualDamage);

            System.out.println(attacker.getClass().getSimpleName() + " attacks with roll " + attackRoll + ". " +
                    defender.getClass().getSimpleName() + " defends with roll " + defendRoll + ". " +
                    "Damage inflicted: " + actualDamage + ". " +
                    defender.getClass().getSimpleName() + "'s health reduced to " + defender.getHealth() + ".");

            // Switch roles for next round
            Player temp = attacker;
            attacker = defender;
            defender = temp;
        }

        if (playerA.getHealth() == 0) {
            System.out.println("Player A has been defeated!");
        } else {
            System.out.println("Player B has been defeated!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Player playerA = new Player(50, 5, 10);
        Player playerB = new Player(100, 10, 5);

        MagicalArena arena = new MagicalArena(playerA, playerB);
        arena.startMatch();
    }
}
