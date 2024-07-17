/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gym;

/**
 *
 * @author hevin
 */
public class Attendance {
    private String id;
    private String name;
    private String inTime;
    private String outTime;

    public Attendance() {
    }

    public Attendance(String id, String name, String inTime, String outTime) {
        this.id = id;
        this.name = name;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
