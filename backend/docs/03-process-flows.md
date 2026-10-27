# Process Flows

The diagrams use Mermaid, which GitHub shows as pictures.

## 1. Current state (as-is): email and spreadsheet
```mermaid
flowchart TD
    subgraph Employee
        A([Problem happens]) --> B[Send email to the IT inbox]
    end
    subgraph IT_Agent[IT agent]
        C[Read the inbox] --> D[Copy details into the spreadsheet]
        D --> E[Decide by feel what to do first]
        E --> F[Fix the problem]
        F --> G[Reply by email]
    end
    B --> C
    G --> H([Employee told])
```
Pain points: emails can be missed, there is no priority or deadline, and the manager cannot see the workload.

## 2. Future state (to-be): helpdesk tickets
```mermaid
flowchart TD
    subgraph Employee
        A([Problem happens]) --> B[Create a ticket with priority]
    end
    subgraph System
        C{Input valid?}
        C -- No --> D[Show a clear error]
        D --> B
        C -- Yes --> E[Save ticket as open and set SLA from priority]
    end
    subgraph IT_Manager[IT manager]
        F[Assign ticket to an agent]
        M[Check summary of open tickets]
    end
    subgraph IT_Agent[IT agent]
        G[Set status to in progress]
        H[Fix the problem]
        I[Set status to resolved]
    end
    subgraph Requester
        J{Problem fixed?}
        J -- Yes --> K[Ticket closed]
        J -- No --> L[Ticket reopened]
    end
    B --> C
    E --> F
    E --> M
    F --> G --> H --> I --> J
    L --> G
```

## 3. Ticket status workflow (business rule BR-02)
```mermaid
stateDiagram-v2
    [*] --> open
    open --> in_progress
    in_progress --> resolved
    resolved --> closed
    resolved --> open: reopen
    closed --> [*]
```
