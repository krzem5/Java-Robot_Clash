package com.krzem.robot_clash;



import java.awt.Graphics2D;



public class Engine extends Constants{
	public Main cls;
	public Board b;
	public int t;
	public AI ai;



	public Engine(Main cls){
		this.cls=cls;
		this.b=new Board(this.cls,this);
		this._gen_board();
		this.t=1;
		this.ai=new AI(this.cls,this,-1);
	}



	public void update(){
		if (this.b.w!=0){
			if (this.cls.KEYBOARD.pressed(82)){
				System.out.println(this.b.w);
				this.b=new Board(this.cls,this);
				this._gen_board();
				this.t=1;
			}
			return;
		}
		for (int i=0;i<3;i++){
			for (int j=0;j<4;j++){
				if (this.b.b[j][i]!=null){
					this.b.b[j][i].update();
				}
			}
		}
		if (this.ai!=null&&this.b.w==0){
			this.ai.update();
		}
	}



	public void draw(Graphics2D g){
		g.setColor(BG_COLOR);
		g.fillRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		g.setColor(BOARD_BG_COLOR);
		g.fillRect(BOARD_RECT.x-BOARD_TILE_OFFSET,BOARD_RECT.y-BOARD_TILE_OFFSET,BOARD_RECT.width+BOARD_TILE_OFFSET*2,BOARD_RECT.height+BOARD_TILE_OFFSET*2);
		for (int i=0;i<3;i++){
			for (int j=0;j<4;j++){
				g.setColor(BOARD_TILE_COLOR);
				g.fillRect(BOARD_RECT.x+i*BOARD_TILE_SIZE+BOARD_TILE_OFFSET,BOARD_RECT.y+j*BOARD_TILE_SIZE+BOARD_TILE_OFFSET,BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2,BOARD_TILE_SIZE-BOARD_TILE_OFFSET*2);
				if (this.b.b[j][i]!=null){
					this.b.b[j][i].draw(g);
				}
			}
		}
	}



	private void _gen_board(){
		this.b.b=new Piece[4][3];
		this.b.b[0][0]=new Piece(this.cls,this,this.b,0,0,"green",1);
		this.b.b[0][1]=new Piece(this.cls,this,this.b,1,0,"red",1);
		this.b.b[0][2]=new Piece(this.cls,this,this.b,2,0,"white",1);
		this.b.b[1][1]=new Piece(this.cls,this,this.b,1,1,"yellow",1);
		this.b.b[3][0]=new Piece(this.cls,this,this.b,0,3,"white",-1);
		this.b.b[3][1]=new Piece(this.cls,this,this.b,1,3,"red",-1);
		this.b.b[3][2]=new Piece(this.cls,this,this.b,2,3,"green",-1);
		this.b.b[2][1]=new Piece(this.cls,this,this.b,1,2,"yellow",-1);
	}
}