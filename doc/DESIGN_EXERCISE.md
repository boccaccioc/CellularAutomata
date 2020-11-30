# Simulation Lab Discussion
## Colin Boccaccio (cmb171), Isabella Knox (ik63), Malvika Jain (msj24)


## Rock Paper Scissors

### High Level Design Ideas


### CRC Card Classes

This class's purpose is to manage interactions between the weapons:
```java
 public class Game {
     public int getWinner (List<Player>)
     public Value getValue ()
 }
```

```java
 public class Player {
     public Player(int score)
     public int getScore ()
     public void setScore (int newScore)
     public Weapon getWeapon ()
     public void setWeapon()
 }
```

This class's purpose is to be the parent class for the weapon types:
```java
 public class Weapon {
     public List<Weapon> getBeatsList
     public List<Weapon> getLosesList 
 }
```

This class's purpose is to represent the paper weapon:
```java
 public class Paper extends Weapon {
     public List<Weapon> getBeatsList
     public List<Weapon> getLosesList 
 }
```

This class's purpose is to represent the Scissors weapon:
```java
 public class Scissors extends Weapon {
     public List<Weapon> getBeatsList
     public List<Weapon> getLosesList 
 }
```

This class's purpose is to represent the Rock weapon:
```java
 public class Rock extends Weapon {
     public List<Weapon> getBeatsList
     public List<Weapon> getLosesList 
 }
```

### Use Cases

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java

// for(int i=0; i<numPlayer;i++){
//     string playerName = "p"+i;
//     Player "p"+i = new Player();
// }
List<Players> playerList = new ArrayList(Arrays.asList(new Player(0),new Player(0), new Player(0),
new Player(0), new Player(0)))

 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
p1.setWeapon(weapon)
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```


## Cell Society

### High Level Design Ideas


### CRC Card Classes

This class's purpose or value is to manage something:
```java
public class Something {
    public int getTotal (Collection<Integer> data)
    public Value getValue ()
}
```

This class's purpose or value is to be useful:
```java
public class Value {
    public void update (int data)
}
```

### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state
```java
Something thing = new Something();
Value v = thing.getValue()
v.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
