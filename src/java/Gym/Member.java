/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gym;

/**
 *
 * @author hevin
 */

public class Member {
    private int id;
    private String name;
    private int height;
    private int weight;
    private String dueDate;

    public Member(int id, String name, int height, int weight, String dueDate) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.dueDate = dueDate;
    }

    Member(int id, String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getDueDate() {
        return dueDate;
    }
}
