package com.krzem.robot_clash;



public class Board{
	public Main cls;
	public Engine e;
	public Piece[][] b;
	public Piece[][] dpl;
	public int[] dpli;
	public int w;



	public Board(Main cls,Engine e){
		this.cls=cls;
		this.e=e;
		this.b=new Piece[4][3];
		this.dpl=new Piece[2][4];
		this.dpli=new int[2];
		this.w=0;
	}



	public Board(Board b){
		this.cls=b.cls;
		this.e=b.e;
		this.b=new Piece[4][3];
		this.dpl=new Piece[2][4];
		this.dpli=new int[2];
		for (int y=0;y<4;y++){
			for (int x=0;x<3;x++){
				if (b.b[y][x]!=null){
					this.b[y][x]=new Piece(b.b[y][x]);
					this.b[y][x].b=this;
				}
			}
		}
		for (int i=0;i<2;i++){
			for (int j=0;j<4;j++){
				if (b.dpl[i][j]!=null){
					this.dpl[i][j]=new Piece(b.dpl[i][j]);
					this.dpl[i][j].b=this;
				}
			}
			this.dpli[i]=b.dpli[i]+0;
		}
		this.w=b.w;
	}



	public void move(Piece p,int nx,int ny){
		this.b[p.y][p.x]=null;
		if (this.b[ny][nx]!=null){
			this.b[ny][nx].d=-this.b[ny][nx].d;
			this.dpl[(p.d==-1?1:0)][this.dpli[(p.d==-1?1:0)]]=this.b[ny][nx];
			this.dpli[(p.d==-1?1:0)]++;
			if (this.b[ny][nx].c.equals("red")){
				this.w=p.d;
			}
		}
		this.b[ny][nx]=p;
		p.x=nx;
		p.y=ny;
		if (ny==(int)(1.5+1.5*p.d)&&p.c.equals("yellow")){
			p.c+="-B";
		}
		else if (ny==(int)(1.5+1.5*p.d)&&p.c.equals("red")){
			this.w=p.d;
		}
	}
}