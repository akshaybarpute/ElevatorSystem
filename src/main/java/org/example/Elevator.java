package org.example;

import org.example.requestStrategy.DefaultRequestProcessingStrategy;
import org.example.requestStrategy.RequestProcessingStrategy;

import java.util.HashSet;
import java.util.Set;

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
        this.requests=new HashSet<>();
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
        if(currFloor==maxFloors){
            this.state=ElevatorState.GOING_DOWN;
        }else if(currFloor==1){
            this.state=ElevatorState.GOING_UP;
        }
        Request nextRequest=this.strategy.selectNextRequest(this.requests,this.currFloor,this.state);
        if (nextRequest==null){
            this.state=ElevatorState.REST;
            return;
        }
        System.out.println("Elevator "+this.getId()+" next stop is: "+nextRequest.floor());
        this.requests.remove(nextRequest);
        this.currFloor=nextRequest.floor();
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
