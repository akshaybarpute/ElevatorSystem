- Elevator System Simulation
- Main Thread (Dispatcher) Thread continuously keeps adding floor requests to the Elevator Threads based on the Elevator Selection Strategy.
- Elevator Threads keep Fulfilling those requests. 


- Improvements needed:
  For DEST requests, we are currently passing Elevator object itself in the requests. It is anti-pattern. We should instead assign id field
  to elevator and that should pass id in DEST requests.


<img width="1070" height="787" alt="Screenshot 2026-11-22 at 9 56 37 PM" src="https://github.com/user-attachments/assets/d04bae88-0fc8-4c69-9b35-fa2c91320492" />
