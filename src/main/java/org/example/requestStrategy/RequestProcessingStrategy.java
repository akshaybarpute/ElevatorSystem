package org.example.requestStrategy;

import org.example.ElevatorState;
import org.example.Request;

import java.util.Set;

public interface RequestProcessingStrategy {
    Request selectNextRequest(Set<Request> requestSet, int currentFloor, ElevatorState state);
}
