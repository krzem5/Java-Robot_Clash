package com.krzem.robot_clash;



import java.awt.BasicStroke;
import java.awt.Graphics2D;



public class Piece extends Constants{
	public Main cls;
	public Engine e;
	public Board b;
	public int x;
	public int y;
	public String c;
	public int d;
	private boolean _md=false;



	public Piece(Main cls,Engine e,Board b,int x,int y,String c,int d){
		this.cls=cls;
		this.e=e;
		this.b=b;
		this.x=x;
		this.y=y;
		this.c=c;
		this.d=d;
	}



	public Piece(Piece p){
		this.cls=p.cls;
		this.e=p.e;
		this.x=p.x;
		this.y=p.y;
		this.c=p.c;
		this.d=p.d;
	}



	public void update(){
		if (this.cls.MOUSE==1&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_COUNT==1&&this._md==false){
			this._md=true;
			if (this.e.t!=this.d){
				return;
			}
			for (int i=-1;i<=1;i++){
				for (int j=-1;j<=1;j++){
					if (i==0&&j==0){
						continue;
					}
					int mv=this._can_move(i,j);
					if (mv!=-1){
						if (mv==1){
							int[] r=this._get_click_rect(i,j,PIECE_MOVE_DOT_SIZE);
							if (r[0]<=this.cls.MOUSE_POS.x&&r[1]<=this.cls.MOUSE_POS.y&&r[0]+r[2]>=this.cls.MOUSE_POS.x&&r[1]+r[2]>=this.cls.MOUSE_POS.y){
								this.e.t=-this.e.t;
								this.e.b.move(this,this.x+i,this.y+j);
								return;
							}
						}
					}
				}
			}
		}
		else if (this.cls.MOUSE==0){
			this._md=false;
		}
	}



	public void draw(Graphics2D g){
		if (this.e.b.w==-this.d){
			return;
		}
		g.setColor(PIECE_COLORS.get(this.c));
		g.fillRect(BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_OFFSET+PIECE_SIZE_OFFSET,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_OFFSET+PIECE_SIZE_OFFSET,BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2-PIECE_SIZE_OFFSET*2,BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2-PIECE_SIZE_OFFSET*2);
		g.setColor(PIECE_ARROW_COLOR);
		g.setStroke(new BasicStroke(PIECE_ARROW_STROKE_WEIGHT,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		g.drawLine(BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2-PIECE_ARROW_WIDTH/2,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2-PIECE_ARROW_HEIGHT/2*this.d,BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2+PIECE_ARROW_HEIGHT/2*this.d);
		g.drawLine(BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2+PIECE_ARROW_WIDTH/2,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2-PIECE_ARROW_HEIGHT/2*this.d,BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_SIZE/2+PIECE_ARROW_HEIGHT/2*this.d);
		if (this.e.b.w!=0){
			return;
		}
		for (int i=-1;i<=1;i++){
			for (int j=-1;j<=1;j++){
				if (i==0&&j==0){
					continue;
				}
				int mv=this._can_move(i,j);
				if (mv!=-1){
					int sz=PIECE_MOVE_DOT_SIZE;
					if (this.e.t!=this.d){
						g.setColor(PIECE_MOVE_DOT_DISABLED_COLOR);
					}
					else if (mv==0){
						g.setColor(PIECE_MOVE_DOT_UNABLE_COLOR);
					}
					else{
						g.setColor(PIECE_MOVE_DOT_COLOR);
						int[] r=this._get_click_rect(i,j,sz);
						if (r[0]<=this.cls.MOUSE_POS.x&&r[1]<=this.cls.MOUSE_POS.y&&r[0]+r[2]>=this.cls.MOUSE_POS.x&&r[1]+r[2]>=this.cls.MOUSE_POS.y){
							sz=PIECE_MOVE_DOT_EXPAND_SIZE;
						}
					}
					int[] r=this._get_click_rect(i,j,sz);
					g.fillRect(r[0],r[1],r[2],r[3]);
				}
			}
		}
	}



	public int _can_move(int mx,int my){
		boolean ok=true;
		if (this.x+mx<0||this.x+mx>2||this.y+my<0||this.y+my>3||(this.b.b[this.y+my][this.x+mx]!=null&&this.b.b[this.y+my][this.x+mx].d==this.d)){
			ok=false;
		}
		for (int[] mv:PIECE_MOVEMENTS.get(this.c)){
			if (mx==mv[0]&&my==mv[1]*this.d){
				return (ok==true?1:0);
			}
		}
		return -1;
	}



	public int _eval_value(){
		if (this.c.equals("red")){
			return 1000;
		}
		if (this.c.equals("white")||this.c.equals("green")){
			return 5;
		}
		if (this.c.equals("yellow-B")){
			return 3;
		}
		return 1;
	}



	private int[] _get_click_rect(int i,int j,int sz){
		return new int[]{BOARD_RECT.x+this.x*BOARD_TILE_SIZE+BOARD_TILE_OFFSET+PIECE_SIZE_OFFSET+(i==-1?PIECE_MOVE_DOT_OFFSET:(i==0?BOARD_TILE_SIZE/2-BOARD_TILE_OFFSET-PIECE_SIZE_OFFSET:BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2-PIECE_SIZE_OFFSET*2-PIECE_MOVE_DOT_OFFSET))-sz/2,BOARD_RECT.y+this.y*BOARD_TILE_SIZE+BOARD_TILE_OFFSET+PIECE_SIZE_OFFSET+(j==-1?PIECE_MOVE_DOT_OFFSET:(j==0?BOARD_TILE_SIZE/2-BOARD_TILE_OFFSET-PIECE_SIZE_OFFSET:BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2-PIECE_SIZE_OFFSET*2-PIECE_MOVE_DOT_OFFSET))-sz/2,sz,sz};
	}
}