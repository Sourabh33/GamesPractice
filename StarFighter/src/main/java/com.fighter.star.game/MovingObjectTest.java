package com.fighter.star.game;

public class MovingObjectTest {
    public static void main(String[] args) {
        System.out.println("****************<Test start>**********************");
        MovingObject ship = new SpaceShip();
        System.out.println("ship1: "+ ship.toString());

        MovingObject ship1 = new SpaceShip(50,75);
        System.out.println("ship2: "+ ship1.toString());

        MovingObject ship2 = new SpaceShip();
        ship2.setX(3);
        ship2.setY(5);
        System.out.println("ship3: "+ ship2.toString());
        System.out.println("****************<Test stop>**********************");
    }
}
