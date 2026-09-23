import json
import random
import time
from datetime import datetime, timezone

from kafka import KafkaProducer


# Kafka producer
producer = KafkaProducer(
    bootstrap_servers=[
        "localhost:29092",
        "localhost:29094",
        "localhost:29096"
    ],
    value_serializer=lambda value: json.dumps(value).encode("utf-8")
)


users = list(range(1, 101))

merchants = [
    "Amazon",
    "Flipkart",
    "Myntra",
    "Swiggy",
    "Uber"
]

payment_types = [
    "UPI",
    "CARD",
    "NET_BANKING",
    "WALLET"
]


while True:

    event = {
        "event_id": random.randint(100000, 999999),
        "user_id": random.choice(users),
        "amount": round(random.uniform(50, 5000), 2),
        "merchant": random.choice(merchants),
        "payment_type": random.choice(payment_types),
        "event_time": datetime.now(timezone.utc).isoformat()
    }

    # Send event to Kafka
    producer.send("transactions", value=event).get(timeout=10)

    print("Sent:", event)

    time.sleep(1)
