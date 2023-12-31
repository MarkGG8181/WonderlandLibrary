package epsilon.events.listeners.packet;

import epsilon.events.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event<EventReceivePacket> {
    public Packet packet;

    public EventReceivePacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
