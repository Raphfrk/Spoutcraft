package net.minecraft.src;

import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.getspout.spout.player.*; //bukkitcontrib

public class GuiChat extends GuiScreen {
	//Spout Improved Chat Start
	public String message = "";
	public int updateCounter = 0;
	public static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;
	
	public int cursorPosition = 0;
	public GuiChat() {
		Spout.getChatManager().chatScroll = 0;
		Spout.getChatManager().commandScroll = 0;
	}
	//Spout Improved Chat End

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void keyTyped(char var1, int var2) {
		//Spout Improved ChatStart
		if (Spout.getChatManager().onChatKeyTyped(var1, var2, this)) {
			return;
		}
		//Spout Improved Chat End
		if(var2 == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
		} else if(var2 == 28) {
			String var3 = this.message.trim();
			if(var3.length() > 0) {
				String var4 = this.message.trim();
				//Spout Improved Chat Start
				if (var4.startsWith("/")) {
					Spout.getChatManager().pastCommands.add(var4);
				}
				//Spout Improved Chat End
				//if(!this.mc.lineIsCommand(var4)) {
				if (!Spout.getChatManager().handleCommand(var4)) {
					//Spout Improved Chat  Start
					Spout.getChatManager().sendChat(var4);
					//Spout Improved Chat End
					//this.mc.thePlayer.sendChatMessage(var4);
					
				}
			}

			this.mc.displayGuiScreen((GuiScreen)null);
		} else {
			if(var2 == 14 && this.message.length() > 0) {
				this.message = this.message.substring(0, this.message.length() - 1);
			}

			if(allowedCharacters.indexOf(var1) >= 0 && this.message.length() < 100) {
				this.message = this.message + var1;
			}

		}
	}

	public void drawScreen(int i, int j, float f) {
		//Spout Improved Chat Start
		Spout.getChatManager().handleMouseWheel();
		boolean blink = ((updateCounter / 6) % 2 != 0);
		String text = message;
		if (cursorPosition > 0 && cursorPosition < message.length()) {
			if (!blink) {
				text = message.substring(0, cursorPosition) + " " + message.substring(cursorPosition);
			}
			else {
				text = message.substring(0, cursorPosition) + "_" + message.substring(cursorPosition);
			}
		}
		else if (cursorPosition == message.length() && blink) {
			text += "_";
		}
		java.util.ArrayList<String> lines = Spout.getChatManager().formatChat(text);
		drawRect(2, height - 2 - (lines.size() * 12), width - 2, height - 2, 0x80000000);
		int size = lines.size();
		for (int k = 0; k < lines.size(); k++) {
			String line = lines.get(k);
			drawString(fontRenderer, line, 4, height - 12 * size--, 0xe0e0e0);
		}
		//Spout Improved Chat End
		super.drawScreen(i, j, f);
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		if(var3 == 0) {
			if(this.mc.ingameGUI.field_933_a != null) {
				if(this.message.length() > 0 && !this.message.endsWith(" ")) {
					this.message = this.message + " ";
				}

				this.message = this.message + this.mc.ingameGUI.field_933_a;
				//Spout Improved Chat Start
				/*
				byte var4 = 100;
				if(message.length() > var4)
				{
					message = message.substring(0, var4);
				}
				*/
				super.drawScreen(var1, var2, var3);
				//Spout Improved Chat End
			} else {
				super.mouseClicked(var1, var2, var3);
			}
		}

	}

}
