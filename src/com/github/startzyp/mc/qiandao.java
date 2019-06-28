package com.github.startzyp.mc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class qiandao extends JavaPlugin {
    private HashMap<Integer,String> info = new HashMap<>();

    @Override
    public void onEnable(){
        ReloadConfig();
        new DaoTool(getDataFolder()+File.separator+"qiandao");
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player&&label.equalsIgnoreCase("qd")){
            int InfoDay = DaoTool.GetPlayerDay(sender.getName());
            Calendar now = Calendar.getInstance(Locale.CHINA);
            int nowday = now.get(Calendar.DAY_OF_MONTH);
            if (InfoDay==nowday){
                NmsJunk.sendTitle((Player) sender,5,8,5,"§e§l[签到插件]","§c§l您已经签到过了");
            }else {
                if (InfoDay==-1){
                    //添加新的并且执行指令
                    DaoTool.AddData(sender.getName(),String.valueOf(nowday));
                    NmsJunk.sendTitle((Player) sender,5,3,5,"§e§l[签到"+String.valueOf(nowday)+"号]","§c§恭喜您签到成功啦");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),info.get(nowday).replace("{Player}",sender.getName()));
                }else {
                    //更新旧的并且执行指令
                    DaoTool.Updata(sender.getName(),String.valueOf(nowday));
                    NmsJunk.sendTitle((Player) sender,5,3,5,"§e§l[签到"+String.valueOf(nowday)+"号]","§c§恭喜您签到成功啦");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),info.get(nowday).replace("{Player}",sender.getName()));
                }
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    private void ReloadConfig(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!(file.exists())){
            saveDefaultConfig();
        }
        Set<String> mines = getConfig().getConfigurationSection("qiandao").getKeys(false);
        for (String temp:mines){
            String Cmd = getConfig().getString("qiandao."+temp+".cmd");
            System.out.println(temp);
            System.out.println(Cmd);
            info.put(Integer.parseInt(temp),Cmd);
        }
    }
}
