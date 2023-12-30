package com.krzem.robot_clash;



import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();
	public static final int MAX_FPS=60;

	public static final Color BG_COLOR=Constants.color("#ffffff");

	public static final int AI_MINMAX_DEPTH=7;

	public static final Color BOARD_BG_COLOR=Constants.color("#af734b");
	public static final int BOARD_TILE_SIZE=400;
	public static final int BOARD_TILE_OFFSET=20;
	public static final Color BOARD_TILE_COLOR=Constants.color("#ebc364");
	public static final Rectangle BOARD_RECT=new Rectangle(WINDOW_SIZE.width/2-(int)(BOARD_TILE_SIZE*1.5),WINDOW_SIZE.height/2-BOARD_TILE_SIZE*2,BOARD_TILE_SIZE*3,BOARD_TILE_SIZE*4);

	public static final Map<String,Color> PIECE_COLORS=new HashMap<String,Color>(){{
		this.put("red",Constants.color("#e88d71"));
		this.put("white",Constants.color("#d8eaf5"));
		this.put("green",Constants.color("#bee594"));
		this.put("yellow",Constants.color("#efee8e"));
		this.put("yellow-B",Constants.color("#e7d29b"));
	}};
	public static final Map<String,int[][]> PIECE_MOVEMENTS=new HashMap<String,int[][]>(){{
		this.put("red",new int[][]{{-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}});
		this.put("white",new int[][]{{-1,-1},{1,-1},{-1,1},{1,1}});
		this.put("green",new int[][]{{0,-1},{-1,0},{1,0},{0,1}});
		this.put("yellow",new int[][]{{0,1}});
		this.put("yellow-B",new int[][]{{0,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}});
	}};
	public static final int PIECE_SIZE_OFFSET=20;
	public static final Color PIECE_ARROW_COLOR=Constants.color("#4b4b4b");
	public static final int PIECE_ARROW_WIDTH=100;
	public static final int PIECE_ARROW_HEIGHT=40;
	public static final int PIECE_ARROW_STROKE_WEIGHT=15;
	public static final Color PIECE_MOVE_DOT_COLOR=Constants.color("#7bcf06");
	public static final Color PIECE_MOVE_DOT_UNABLE_COLOR=Constants.color("#e01f0b");
	public static final Color PIECE_MOVE_DOT_DISABLED_COLOR=Constants.color("#b3b3b3");
	public static final int PIECE_MOVE_DOT_OFFSET=30;
	public static final int PIECE_MOVE_DOT_SIZE=15;
	public static final int PIECE_MOVE_DOT_EXPAND_SIZE=25;



	private static Color color(String h){
		return new Color(Integer.parseInt(h.substring(1,3),16),Integer.parseInt(h.substring(3,5),16),Integer.parseInt(h.substring(5,7),16));
	}
}
