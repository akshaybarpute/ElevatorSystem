package org.example.requestAssignmentStategy;

import org.example.Elevator;

import java.util.List;

public interface ElevatorSystemRequestAssignmentStategy {
    public Elevator getElevatorForNextRequest(List<Elevator> elevators);
}
