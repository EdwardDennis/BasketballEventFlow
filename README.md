# Basketball Game Event Streaming
This project is a simple basketball game event streaming service. It reads game events data from sample files, decodes the hexadecimal data and processes each event to simulate a real-time game event stream. The motivation behind this design is to ensure the quickest delivery and processing of game event data. This realtime processing is crucial in contexts where it's essential to make immediate decisions based on the streamed data, such as in live betting, real-time analytics, or immediate fan engagement.

## How it Works

The logic of the application is controlled from the `GameController`. Here is a step-by-step overview of how it operates:

1. `GameController` invokes `DataController` to fetch data from the provided sample data. The data returned by `DataController` is a list of integers representing hexadecimal values.

2. This data is then forwarded to `EventController` for decoding.

3. `EventController` takes each hexadecimal value and decodes them into `Event` instances.

4. The decoded `Event` data is then sent back to `GameController`.

5. `GameController` performs validation on each `Event` against previously stored events. For instance, an event is considered valid if its match time is later than previous events and it maintains logical point progression (e.g., the points are within a valid range).

6. Valid events are then added to an event store for further processing.

For event storage, the system uses a `TrieMap`. The `TrieMap` is a thread-safe concurrent data structure, which is effective for this use case, where concurrent access and modification of the event store is common. A `TrieMap` offers efficient, non-blocking, snapshot and look-up operations in constant time (O(1)). This is especially important for this application since consistent analysis of the stored events is a key operation.

### To run with sample 1 data:
``sbt 'run sample1'``

### To run with sample 2 data:
``sbt 'run sample2'``

### To run tests:
``sbt test``
