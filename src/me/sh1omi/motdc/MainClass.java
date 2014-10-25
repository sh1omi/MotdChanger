package me.sh1omi.motdc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;



public class MainClass extends JavaPlugin implements Listener{
	
	public static MainClass plugin;
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[ " + pdfFile.getName() + " ] Version: " +  pdfFile.getVersion() + " Has been disabled");
		
	}
	
	boolean status = false;
	List<String> list = new ArrayList<String>();
	int num = 0;
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		getServer().getPluginManager().registerEvents(this, this);
		this.logger.info("[ " + pdfFile.getName() + " ] Version: " +  pdfFile.getVersion() + " Has been enabled");
		
		this.saveDefaultConfig();
		list = this.getConfig().getStringList("MotdList");
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String commandLabel,String[] args){
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(commandLabel.equalsIgnoreCase("motdc"))
			{
				if(player.hasPermission("motdc"))
				{
					if(args.length == 0)
						player.sendMessage(ChatColor.DARK_RED + "[MotdC] /MotdC [help / toogle / show / reload ]");
					else if(args[0].equals("help"))
					{
						player.sendMessage(ChatColor.GREEN + "[MotdC] /MotdC help - Show this list.");
						player.sendMessage(ChatColor.GREEN + "[MotdC] /MotdC toogle - Activate or Deactivate the plugin");
						player.sendMessage(ChatColor.GREEN + "[MotdC] /MotdC show - Show the config file");
						player.sendMessage(ChatColor.GREEN + "[MotdC] /MotdC reload - Reload the config file");
						player.sendMessage(ChatColor.GREEN + "[MotdC] Thanks for sh1omi2(skype) that build our plugin :)");
					}
					else if(args[0].equals("toogle"))
						if(status)
						{
							player.sendMessage(ChatColor.GREEN + "[MotdC] Deactivated motd changer!");
							status = false;
						}
						else
						{
							player.sendMessage(ChatColor.GREEN + "[MotdC] Activated motd changer!");
							status = true;
						}
					else if(args[0].equals("show"))
						for(int i=0;i<list.size();i++)
							player.sendMessage(ChatColor.GREEN + list.get(i));
					else if(args[0].equals("reload"))
					{
						list = this.getConfig().getStringList("MotdList");
						player.sendMessage(ChatColor.GREEN + "[MotdC] The list reloaded, do '/MotdC show' to check the list.");
					}
					else player.sendMessage(ChatColor.DARK_RED + "[MotdC] /MotdC [help / toogle / show / reload ]");
					
				} else
					player.sendMessage(ChatColor.DARK_RED + "[MotdC] You dont have the permission to do that!");
				return true;
			}
		} else
		{
			if(args.length == 0)
				System.out.print(Ansi.ansi().fg(Color.RED)+ "[MotdC] /MotdC [help / toogle / show / reload ]"+ Ansi.ansi().fg(Color.WHITE));
			else if(args[0].equals("help"))
			{
				System.out.print(Ansi.ansi().fg(Color.GREEN) + "[MotdC] /MotdC help - Show this list.");
				System.out.print("[MotdC] /MotdC toogle - Activate or Deactivate the plugin");
				System.out.print("[MotdC] /MotdC show - Show the config file");
				System.out.print("[MotdC] /MotdC reload - Reload the config file");
				System.out.print("[MotdC] Thanks for sh1omi2(skype) that build our plugin :)"+ Ansi.ansi().fg(Color.WHITE));
			}
			else if(args[0].equals("toogle"))
				if(status)
				{
					System.out.print(Ansi.ansi().fg(Color.GREEN)+"[MotdC] Deactivated motd changer!"+ Ansi.ansi().fg(Color.WHITE));
					status = false;
				}
				else
				{
					System.out.print(Ansi.ansi().fg(Color.GREEN) + "[MotdC] Activated motd changer!"+ Ansi.ansi().fg(Color.WHITE));
					status = true;
				}
			else if(args[0].equals("show"))
				for(int i=0;i<list.size();i++)
					System.out.print(Ansi.ansi().fg(Color.GREEN) + list.get(i)+ Ansi.ansi().fg(Color.WHITE));
			else if(args[0].equals("reload"))
			{
				list = this.getConfig().getStringList("MotdList");
				System.out.print(Ansi.ansi().fg(Color.GREEN) +"[MotdC] The list reloaded, do '/MotdC show' to check the list."+ Ansi.ansi().fg(Color.WHITE));
			}
			else System.out.print(Ansi.ansi().fg(Color.GREEN) +"[MotdC] /MotdC [help / toogle / show / reload ]"+ Ansi.ansi().fg(Color.WHITE));
			return true;
		}
		return false;
		
	}

	 @EventHandler
	public void onServerListPing(ServerListPingEvent event) {
	        if(status)
	        {
	        	if(num == list.size())
	        		num = 0;
	        	event.setMotd(replaceColors(list.get(num)));
	        	num++;
	        }
	 }
	
	public static String replaceColors(String string){
		return string.replaceAll("(?i)&([a-k0-9])", "\u00A7$1");
		}
	
}
