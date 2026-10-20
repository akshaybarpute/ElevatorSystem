package org.example.requestAssignmentStategy;

import org.example.Elevator;

import java.util.List;

public class LeastBusyElevatorSystemRequestAssignmentStrategy implements ElevatorSystemRequestAssignmentStategy{
    @Override
    public Elevator getElevatorForNextRequest(List<Elevator> elevators) {
        if(elevators==null){
            return null;
        }
        return elevators.stream()
                .min((ele1,ele2)->{
                    return Integer.compare(ele1.getCurrentRequestCount(), ele2.getCurrentRequestCount());
                }).orElse(null);
    }
}
