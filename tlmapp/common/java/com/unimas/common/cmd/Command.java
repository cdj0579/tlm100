package com.unimas.common.cmd;

import java.util.ArrayList;
import java.util.List;

/**命令对象
 * @author cj
 *
 */
public class Command {
	private List<String> commands = new ArrayList<String>();
    public Command append(String cmd){
    	commands.add(cmd);
    	return this;
    }
    
    public List<String> getCommand(){
    	return commands;
    }
}
