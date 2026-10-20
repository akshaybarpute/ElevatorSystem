package org.example;

import org.example.requestAssignmentStategy.ElevatorSystemRequestAssignmentStategy;
import org.example.requestAssignmentStategy.LeastBusyElevatorSystemRequestAssignmentStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorSystem {
    private final List<Elevator> elevetors;
    private ElevatorSystemRequestAssignmentStategy stategy;

    public ElevatorSystem(List<Elevator> elevetors){
        this.elevetors=elevetors;
        System.out.println("Elevators: "+this.elevetors.size());
        this.stategy=new LeastBusyElevatorSystemRequestAssignmentStrategy();
    }
    public boolean registerRequest(Request request){
        if(this.elevetors.size()==0){
            return false;
        }
        Elevator elevator=this.stategy.getElevatorForNextRequest(this.elevetors);
        System.out.println("Request "+request+" is assigned to Elevator: "+elevator.getId());
        elevator.registerRequest(request);
        return true;
    }
    public void start(){
        ExecutorService executorService= Executors.newFixedThreadPool(this.elevetors.size());
        for(Elevator elevator:this.elevetors){
            executorService.submit(elevator);
        }
    }
}
