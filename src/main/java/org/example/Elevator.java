package org.example;

import org.example.requestStrategy.DefaultRequestProcessingStrategy;
import org.example.requestStrategy.RequestProcessingStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Elevator implements Runnable{
    private ElevatorState state;
    private int currFloor;
    private int maxFloors;
    private final Set<Request> requests;
    private RequestProcessingStrategy strategy;
    private int id;
    public Elevator(){
        this.state=ElevatorState.REST;
        this.currFloor=0;
        this.requests= ConcurrentHashMap.newKeySet();
        this.strategy=new DefaultRequestProcessingStrategy();
        this.maxFloors=12;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public void setState(ElevatorState state){
        this.state=state;
    }
    public void registerRequest(Request request){
        this.requests.add(request);
    }
    public int getCurrentRequestCount(){
        return this.requests.size();
    }
    private void step() throws InterruptedException {
        boolean serviced = this.requests.removeIf(req -> req.floor() == this.currFloor);
        if (serviced) {
            System.out.println("Elevator " + this.getId() + " opened doors and serviced floor: " + this.currFloor);
            Thread.sleep(1000); // Door open/close time
        }

        if(requests.size()==0){
            this.state=ElevatorState.REST;
            return;
        }

        Request nextRequest=this.strategy.selectNextRequest(this.requests,this.currFloor,this.state);
        if(nextRequest==null){
            this.state= this.state.equals(ElevatorState.GOING_UP) || this.state.equals(ElevatorState.REST) ? ElevatorState.GOING_DOWN : ElevatorState.GOING_UP;
            Thread.sleep(1000);
            return;
        }
//        System.out.println("Elevator "+this.getId()+" next stop is: "+nextRequest.floor());

        if(nextRequest.floor()>this.currFloor){
            this.state=ElevatorState.GOING_UP;
            this.currFloor++;
        } else if(nextRequest.floor()<this.currFloor){
            this.state=ElevatorState.GOING_DOWN;
            this.currFloor--;
        }

        System.out.println("Elevator " + this.getId() + " is at floor " + this.currFloor + " (" + this.state + ")");
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        try {
            while(true){
                this.step();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
