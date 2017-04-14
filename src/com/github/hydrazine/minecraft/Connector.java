package com.github.hydrazine.minecraft;

import java.util.ArrayList;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

/**
 * 
 * @author xTACTIXzZ
 * 
 * This class handles the connections of the clients
 *
 */
public class Connector 
{
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public Connector()
	{
		
	}
	
	/**
	 * Connects a client to a server
	 * @param client A client
	 */
	public void connect(Client client)
	{
		addDefaultListeners(client);
		
		client.getSession().connect();
	}
	
	public void disconnect(Client client)
	{
		client.getSession().disconnect("Disconnected by program.");
	}
	
	/*
	 * Adds default listeners to a client
	 */
	private void addDefaultListeners(Client client)
	{
		client.getSession().addListener(new SessionAdapter() 
		{
            @Override
            public void disconnected(DisconnectedEvent event) 
            {                
                if(clients.contains(client))
                {
                	clients.remove(client);
                }
                else
                {
                	System.out.println("Client list does not contain player: " + ((MinecraftProtocol) client.getPacketProtocol()).getProfile().getName());
                }
            }
            
            @Override
            public void connected(ConnectedEvent event) 
            {                
                if(clients.contains(client))
                {
                	System.out.println("Client list does already contain player: " + ((MinecraftProtocol) client.getPacketProtocol()).getProfile().getName());
                }
                else
                {
                	clients.add(client);
                }
                
                System.out.println(((MinecraftProtocol) client.getPacketProtocol()).getProfile().getName() + " connected to the server!");
            }
        });
	}

}