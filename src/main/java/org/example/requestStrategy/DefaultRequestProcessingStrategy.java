package org.example.requestStrategy;

import org.example.ElevatorState;
import org.example.Request;
import org.example.RequestType;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRequestProcessingStrategy implements RequestProcessingStrategy{

    @Override
    public Request selectNextRequest(Set<Request> requestSet, int currentFloor, ElevatorState state) {
        RequestType type= state.equals(ElevatorState.GOING_UP) || state.equals(ElevatorState.REST) ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
        return requestSet.stream()
                .filter(request -> request.type().equals(type))
                .filter(request -> {
                    if(type.equals(RequestType.PICKUP_UP)){
                        return request.floor()>=currentFloor;
                    }
                    return request.floor()<=currentFloor;
                })
                .min(
                    Comparator.comparingInt(req->Math.abs(req.floor()-currentFloor))
                ).orElse(null);
    }
}
