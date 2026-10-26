package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Elevator> l=new ArrayList<>();
//        System.out.println("Hello World");
        Elevator elevator1=new Elevator();
        elevator1.setId(1);
        l.add(elevator1);
        Elevator elevator2=new Elevator();
        elevator2.setId(2);
        l.add(elevator2);
        Elevator elevator3=new Elevator();
        elevator3.setId(3);
        l.add(elevator3);
        Elevator elevator4=new Elevator();
        elevator4.setId(4);
        l.add(elevator4);
        Elevator elevator5=new Elevator();
        elevator5.setId(5);
        l.add(elevator5);
        Elevator elevator6=new Elevator();
        elevator6.setId(6);
        l.add(elevator6);

        ElevatorSystem elevatorSystem=new ElevatorSystem(l);
        elevatorSystem.start();

        Thread t =new Thread(()->{
            while(true){
                int nextFloor=ThreadLocalRandom.current().nextInt(1, 13); // Upper bound is exclusive
                boolean dir=ThreadLocalRandom.current().nextBoolean();
                RequestType type = dir ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
                elevatorSystem.registerRequest(new Request(nextFloor,type,null));
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();
        System.out.println("Manual requests are added in the queue");
        elevatorSystem.registerRequest(new Request(10,RequestType.DESTINATION,l.get(0)));
        elevatorSystem.registerRequest(new Request(1,RequestType.DESTINATION,l.get(1)));
    }
}